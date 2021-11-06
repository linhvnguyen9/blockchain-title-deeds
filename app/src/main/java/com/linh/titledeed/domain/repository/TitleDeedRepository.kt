package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.entity.*
import com.linh.titledeed.domain.utils.Resource

interface TitleDeedRepository {
    suspend fun getAllOwnedDeeds(address: String): List<Deed>
    suspend fun getDeedDetail(tokenId: String): Deed
    suspend fun getTokenOwner(tokenId: String): String
    suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): Resource<TransferOwnershipTransaction>
    suspend fun transferOwnership(transaction: TransferOwnershipTransaction): Resource<Any>
    suspend fun estimateGasCreateSale(transaction: CreateSaleTransaction): Resource<CreateSaleTransaction>
    suspend fun createSale(transaction: CreateSaleTransaction): Resource<Any>
    suspend fun estimateGasCancelSale(transaction: CancelSaleTransaction): Resource<CancelSaleTransaction>
    suspend fun cancelSale(transaction: CancelSaleTransaction): Resource<Any>
    suspend fun estimateGasBuy(transaction: BuyTransaction): Resource<BuyTransaction>
    suspend fun buy(transaction: BuyTransaction): Resource<Any>
    suspend fun getAllSales(): List<Sale>
    suspend fun getSaleInfo(tokenId: String): Sale
    suspend fun uploadSaleMetadata(sale: Sale): Resource<String>
}