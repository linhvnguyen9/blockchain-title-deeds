package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

data class CreateWalletUseCase @Inject constructor(private val walletRepository: WalletRepository) {
    suspend operator fun invoke(password: String) = walletRepository.createWallet(password)
}