package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction
import com.linh.titledeed.domain.utils.Resource

interface TitleDeedRepository {
    suspend fun getAllOwnedDeeds(address: String): List<Deed>
    suspend fun getDeedDetail(tokenId: String): Deed
    suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): Resource<TransferOwnershipTransaction>
    suspend fun transferOwnership(transaction: TransferOwnershipTransaction): Resource<Any>
    suspend fun uploadSaleMetadata(sale: Sale): Resource<Any>
}