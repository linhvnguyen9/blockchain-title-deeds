package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.TitleDeedRepository
import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class CheckContractOwnerUseCase @Inject constructor(private val titleDeedRepository: TitleDeedRepository, private val walletRepository: WalletRepository) {
    suspend operator fun invoke(): Boolean {
        val contractOwner = titleDeedRepository.getContractOwnerAddress()
        val wallet = walletRepository.getWallet()
        return contractOwner == wallet.address
    }
}