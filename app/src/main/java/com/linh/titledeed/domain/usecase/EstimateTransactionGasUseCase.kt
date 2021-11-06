package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.*
import com.linh.titledeed.domain.repository.TitleDeedRepository
import com.linh.titledeed.domain.utils.Resource
import javax.inject.Inject

class EstimateTransactionGasUseCase @Inject constructor(private val titleDeedRepository: TitleDeedRepository) {
    suspend operator fun invoke(transaction: Transaction): Resource<Transaction> {
        return when (transaction) {
            is TransferOwnershipTransaction -> {
                titleDeedRepository.estimateGasTransferOwnership(transaction)
            }
            is CreateSaleTransaction -> {
                titleDeedRepository.estimateGasCreateSale(transaction)
            }
            is CancelSaleTransaction -> {
                titleDeedRepository.estimateGasCancelSale(transaction)
            }
            is BuyTransaction -> {
                titleDeedRepository.estimateGasBuy(transaction)
            }
        }
    }
}