package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction

interface TitleDeedRepository {
    suspend fun getAllOwnedDeeds(address: String): List<Deed>
    suspend fun getDeedDetail(tokenId: String): Deed
    suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): TransferOwnershipTransaction
    suspend fun transferOwnership(transaction: TransferOwnershipTransaction): String
}