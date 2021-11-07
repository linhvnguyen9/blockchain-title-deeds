// SPDX-License-Identifier: MIT
pragma solidity ^0.8.2;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/security/Pausable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Burnable.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";

contract VTitleDeeds is ERC721, ERC721Enumerable, Pausable, Ownable, ERC721Burnable, ERC721URIStorage {
    event DeedOffered(uint indexed itemId, uint price);
    event DeedBought(uint indexed itemId, uint value, address indexed fromAddress, address indexed toAddress);
    event DeedNoLongerForSale(uint indexed itemId);

    struct Offer {
        address seller;
        uint256 itemId;
        uint256 price;
        bool isForSale;
        string metadataUri;
    }

    mapping(uint => Offer) public deedsOfferedForSale;
    mapping(address => uint) public pendingWithdrawals;

    constructor() ERC721("VTitleDeeds", "VTD") {}

    function _baseURI() internal pure override returns (string memory) {
        return "ipfs://";
    }

    function pause() public onlyOwner {
        _pause();
    }

    function unpause() public onlyOwner {
        _unpause();
    }

    function safeMint(address to, uint256 tokenId, string memory _tokenURI) public onlyOwner {
        _safeMint(to, tokenId);
        _setTokenURI(tokenId, _tokenURI);
    }

    function offerForSale(uint itemId, uint salePriceInWei, string memory _metadataUri) public {
        _approve(address(this), itemId);
        deedsOfferedForSale[itemId] = Offer(msg.sender, itemId, salePriceInWei, true, _metadataUri);
        emit DeedOffered(itemId, salePriceInWei);
    }

    function closeSaleOffer(uint itemId) public {
        _closeSaleOffer(itemId);
    }

    function _closeSaleOffer(uint itemId) private {
        Offer memory oldOffer = deedsOfferedForSale[itemId];
        require(oldOffer.seller == msg.sender || address(this) == msg.sender, "You're not the owner of this token");
        deedsOfferedForSale[itemId] = Offer(oldOffer.seller, itemId, 0, false, oldOffer.metadataUri);
        emit DeedNoLongerForSale(itemId);
    }

    function buyDeed(uint itemId) payable public {
        Offer memory offer = deedsOfferedForSale[itemId];
        require(offer.isForSale, "This token is not offered for sale");
        require(msg.value >= offer.price, "Didn't send enough ETH");

        address seller = offer.seller;

        deedsOfferedForSale[itemId] = Offer(offer.seller, itemId, 0, false, offer.metadataUri);
        emit DeedNoLongerForSale(itemId);
        _transfer(offer.seller, msg.sender, itemId);

        pendingWithdrawals[seller] += msg.value;
        emit DeedBought(itemId, msg.value, seller, msg.sender);

        if (msg.value > offer.price) {
            pendingWithdrawals[msg.sender] += msg.value - offer.price;
        }
    }

    function withdraw() public {
        require(pendingWithdrawals[msg.sender] != 0, "There's no funds to withdraw");
        uint amount = pendingWithdrawals[msg.sender];
        // Remember to zero the pending refund before
        // sending to prevent re-entrancy attacks
        pendingWithdrawals[msg.sender] = 0;
        payable(msg.sender).transfer(amount);
    }

    function _beforeTokenTransfer(address from, address to, uint256 tokenId)
    internal
    whenNotPaused
    override(ERC721, ERC721Enumerable)
    {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    // The following functions are overrides required by Solidity.

    function supportsInterface(bytes4 interfaceId)
    public
    view
    override(ERC721, ERC721Enumerable)
    returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }

    function _burn(uint256 tokenId) internal override(ERC721, ERC721URIStorage) {
        super._burn(tokenId);
    }

    function tokenURI(uint256 tokenId)
    public
    view
    override(ERC721, ERC721URIStorage)
    returns (string memory)
    {
        return super.tokenURI(tokenId);
    }

    // Override transfer, cancel trade when transfer deed
    function safeTransferFrom(address from,
        address to,
        uint256 tokenId
    ) override public virtual {
        if (deedsOfferedForSale[tokenId].isForSale) {
            closeSaleOffer(tokenId);
        }

        super.safeTransferFrom(from, to, tokenId);
    }
}