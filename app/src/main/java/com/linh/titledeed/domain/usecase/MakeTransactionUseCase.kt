package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction
import com.linh.titledeed.domain.repository.TitleDeedRepository
import com.linh.titledeed.domain.utils.Resource
import javax.inject.Inject

class MakeTransactionUseCase @Inject constructor (private val titleDeedRepository: TitleDeedRepository) {
    suspend operator fun invoke(transaction: Transaction): Resource<Any> {
        return when (transaction) {
            is TransferOwnershipTransaction -> {
                titleDeedRepository.transferOwnership(transaction)
            }
        }
    }
}