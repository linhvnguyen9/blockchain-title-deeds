package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class SaveWalletUseCase @Inject constructor(private val walletRepository: WalletRepository) {
    suspend operator fun invoke(wallet: Wallet) = walletRepository.saveWallet(wallet)
}