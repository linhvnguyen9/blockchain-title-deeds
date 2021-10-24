package com.linh.titledeed.domain.entity

class Transaction(val type: TransactionType, val gasPriceInWei: String, val senderAddress: String, val receiverAddress: String)

enum class TransactionType {
    TRANSFER_OWNERSHIP
}