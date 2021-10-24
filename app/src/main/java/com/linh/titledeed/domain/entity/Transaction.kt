package com.linh.titledeed.domain.entity

sealed class Transaction(val type: TransactionType, val gasPriceInWei: String, val senderAddress: String, val receiverAddress: String)

class TransferOwnershipTransaction(gasPriceInWei: String = "", senderAddress: String = "", receiverAddress: String, val tokenId: String) : Transaction(TransactionType.TRANSFER_OWNERSHIP, gasPriceInWei, senderAddress, receiverAddress)

enum class TransactionType {
    TRANSFER_OWNERSHIP
}