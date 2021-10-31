package com.linh.titledeed.domain.entity

sealed class Transaction(val type: TransactionType, val gasPriceInWei: String, val senderAddress: String, val receiverAddress: String = "")

class TransferOwnershipTransaction(gasPriceInWei: String = "", senderAddress: String = "", receiverAddress: String, val tokenId: String) : Transaction(TransactionType.TRANSFER_OWNERSHIP, gasPriceInWei, senderAddress, receiverAddress)

class CreateSaleTransaction(gasPriceInWei: String = "", senderAddress: String = "", val tokenId: String, val priceInWei: String, val metadataUri: String) : Transaction(TransactionType.CREATE_SALE, gasPriceInWei, senderAddress)

class CancelSaleTransaction(gasPriceInWei: String = "", senderAddress: String = "", val tokenId: String) : Transaction(TransactionType.CANCEL_SALE, gasPriceInWei, senderAddress)

enum class TransactionType {
    TRANSFER_OWNERSHIP,
    CREATE_SALE,
    CANCEL_SALE
}