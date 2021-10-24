package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction
import com.linh.titledeed.domain.repository.TitleDeedRepository
import javax.inject.Inject

class EstimateTransactionGasUseCase @Inject constructor(private val titleDeedRepository: TitleDeedRepository) {
    suspend operator fun invoke(transaction: Transaction): Transaction {
        return when (transaction) {
            is TransferOwnershipTransaction -> {
                titleDeedRepository.estimateGasTransferOwnership(transaction)
            }
        }
    }
}