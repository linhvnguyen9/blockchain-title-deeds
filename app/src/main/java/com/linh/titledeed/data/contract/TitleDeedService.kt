package com.linh.titledeed.data.contract

import com.linh.titledeed.data.entity.GetSaleDetailResponse
import com.linh.titledeed.domain.entity.*
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.gas.ContractGasProvider
import timber.log.Timber
import java.math.BigInteger
import javax.inject.Inject

class TitleDeedService @Inject constructor(private val web3j: Web3j) {
    private lateinit var smartContract: VTitleDeedsExtensions

    fun initService(wallet: Wallet) {
        val credentials = initCredentials(wallet)
        smartContract = initSmartContract(credentials)
    }

    private fun initCredentials(wallet: Wallet): Credentials {
        val credentials = if (wallet.mnemonic.isNotEmpty()) {
            WalletUtils.loadBip39Credentials(wallet.password, wallet.mnemonic)
        } else {
            val privateKeyBigInteger = BigInteger(wallet.privateKey, 16)
            val keyPair = ECKeyPair.create(privateKeyBigInteger)
            Credentials.create(keyPair)
        }
        Timber.d("Wallet private key ${credentials.ecKeyPair.privateKey.toString(16)}")
        return credentials
    }

    private fun initSmartContract(credentials: Credentials): VTitleDeedsExtensions {
        val contractGasProvider = object :
            ContractGasProvider {
            override fun getGasPrice(contractFunc: String?): BigInteger {
                return web3j.ethGasPrice().send().gasPrice
            }

            override fun getGasPrice(): BigInteger {
                return web3j.ethGasPrice().send().gasPrice
            }

            override fun getGasLimit(contractFunc: String?): BigInteger {
                return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().block.gasLimit
            }

            override fun getGasLimit(): BigInteger {
                return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().block.gasLimit
            }
        }

        return VTitleDeedsExtensions(credentials, web3j, contractGasProvider)
    }

    suspend fun getTotalSupply(): BigInteger = withContext(Dispatchers.IO) {
        return@withContext smartContract.totalSupply().send()
    }

    suspend fun getTokenIdByIndex(index: BigInteger): BigInteger = withContext(Dispatchers.IO) {
        return@withContext smartContract.tokenByIndex(index).send()
    }

    suspend fun getBalance(owner: String): BigInteger =
        withContext(Dispatchers.IO) {
            return@withContext smartContract.balanceOf(owner).send()
        }

    suspend fun getTokenOwner(tokenId: String): String =
        withContext(Dispatchers.IO) {
            return@withContext smartContract.ownerOf(tokenId.toBigInteger()).send()
        }

    suspend fun getTokensOfOwner(ownerAddress: String, index: BigInteger): BigInteger =
        withContext(Dispatchers.IO) {
            return@withContext smartContract.tokenOfOwnerByIndex(ownerAddress, index).send()
        }

    suspend fun getMetadataUri(tokenId: BigInteger): String = withContext(Dispatchers.IO) {
        return@withContext smartContract.tokenURI(tokenId).send()
    }

    suspend fun getContractOwnerAddress(): String = withContext(Dispatchers.IO) {
        return@withContext smartContract.owner().send()
    }

    suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): String =
        withContext(Dispatchers.IO) {
            Timber.d("estimateGasTransferOwnership sender address ${transaction.senderAddress} receiverAddress ${transaction.receiverAddress}")
            Timber.d("estimateGasTransferOwnership tokenId ${transaction.tokenId.toBigDecimal()}")

            return@withContext smartContract.estimateGasSafeTransferFrom(
                transaction.senderAddress,
                transaction.receiverAddress,
                transaction.tokenId.toBigInteger()
            ).send().amountUsed.toString(10)
        }

    suspend fun transferOwnership(transaction: TransferOwnershipTransaction) {
        withContext(Dispatchers.IO) {
            smartContract.safeTransferFrom(
                transaction.senderAddress,
                transaction.receiverAddress,
                transaction.tokenId.toBigInteger()
            ).send()
        }
    }

    suspend fun estimateGasCreateSale(transaction: CreateSaleTransaction): String =
        withContext(Dispatchers.IO) {
            transaction.run {
                return@withContext smartContract.estimateGasCreateSale(
                    tokenId.toBigInteger(),
                    priceInWei.toBigInteger(),
                    metadataUri
                ).send().amountUsed.toString(10)
            }
        }

    suspend fun createSale(transaction: CreateSaleTransaction) {
        withContext(Dispatchers.IO) {
            transaction.run {
                smartContract.offerForSale(
                    tokenId.toBigInteger(),
                    priceInWei.toBigInteger(),
                    metadataUri
                ).send()
            }
        }
    }

    suspend fun estimateGasCancelSale(transaction: CancelSaleTransaction): String =
        withContext(Dispatchers.IO) {
            transaction.run {
                return@withContext smartContract.estimateGasCancelSale(
                    tokenId.toBigInteger(),
                ).send().amountUsed.toString(10)
            }
        }

    suspend fun cancelSale(transaction: CancelSaleTransaction)  {
        withContext(Dispatchers.IO) {
            transaction.run {
                smartContract.closeSaleOffer(
                    tokenId.toBigInteger(),
                ).send()
            }
        }
    }

    suspend fun getSaleDetail(tokenId: String): GetSaleDetailResponse =
        withContext(Dispatchers.IO) {
            val response = smartContract.deedsOfferedForSale(tokenId.toBigInteger()).send()
            val (seller, itemId, price, isForSale, metadataUri) = response
            return@withContext GetSaleDetailResponse(
                seller,
                itemId.toString(10),
                price.toString(10),
                isForSale,
                metadataUri
            )
        }

    suspend fun estimateGasBuy(transaction: BuyTransaction): String = withContext(Dispatchers.IO) {
        transaction.run {
            return@withContext smartContract.estimateGasBuyDeed(
                tokenId.toBigInteger(),
                valueWei.toBigInteger()
            ).send().amountUsed.toString(10)
        }
    }

    suspend fun buy(transaction: BuyTransaction)  {
        withContext(Dispatchers.IO) {
            transaction.run {
                smartContract.buyDeed(
                    tokenId.toBigInteger(),
                    valueWei.toBigInteger()
                ).send()
            }
        }
    }

    fun getContractAddress(): String {
        return VTitleDeedsExtensions.ERC721_SMART_CONTRACT_ADDRESS
    }

    companion object {
        private val ETH_DECIMALS = BigInteger("1000000000000000000")
    }
}