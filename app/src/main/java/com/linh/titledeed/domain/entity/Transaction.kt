package com.linh.titledeed.domain.entity

sealed class Transaction(val type: TransactionType, val gasPriceInWei: String, val senderAddress: String, val receiverAddress: String = "")

class CreateDeedTransaction(gasPriceInWei: String = "", senderAddress: String = "", receiverAddress: String = "", val tokenId: String, val uri: String) : Transaction(TransactionType.CREATE_DEED, gasPriceInWei, senderAddress, receiverAddress)

class TransferOwnershipTransaction(gasPriceInWei: String = "", senderAddress: String = "", receiverAddress: String, val tokenId: String) : Transaction(TransactionType.TRANSFER_OWNERSHIP, gasPriceInWei, senderAddress, receiverAddress)

class CreateSaleTransaction(gasPriceInWei: String = "", senderAddress: String = "", val tokenId: String, val priceInWei: String, val metadataUri: String) : Transaction(TransactionType.CREATE_SALE, gasPriceInWei, senderAddress)

class CancelSaleTransaction(gasPriceInWei: String = "", senderAddress: String = "", val tokenId: String) : Transaction(TransactionType.CANCEL_SALE, gasPriceInWei, senderAddress)

class BuyTransaction(gasPriceInWei: String = "", senderAddress: String = "", val tokenId: String, val valueWei: String) : Transaction(TransactionType.BUY, gasPriceInWei, senderAddress)

enum class TransactionType {
    CREATE_DEED,
    TRANSFER_OWNERSHIP,
    CREATE_SALE,
    CANCEL_SALE,
    BUY
}