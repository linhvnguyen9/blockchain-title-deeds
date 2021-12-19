package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class RestoreWalletUseCase @Inject constructor(private val repository: WalletRepository) {
    suspend operator fun invoke(newPassword: String, mnemonic: String): Wallet {
        val wallet = repository.restoreWallet(newPassword, mnemonic)
        repository.saveWallet(wallet)
        return wallet
    }
    suspend operator fun invoke(privateKey: String): Wallet {
        val wallet = repository.restoreWallet(privateKey)
        repository.saveWallet(wallet)
        return wallet
    }
}