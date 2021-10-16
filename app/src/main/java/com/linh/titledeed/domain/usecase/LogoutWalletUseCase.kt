package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class LogoutWalletUseCase @Inject constructor(private val walletRepository: WalletRepository) {
    operator fun invoke() = walletRepository.logoutWallet()
}