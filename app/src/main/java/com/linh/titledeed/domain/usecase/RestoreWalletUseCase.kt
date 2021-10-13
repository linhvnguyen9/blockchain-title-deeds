package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class RestoreWalletUseCase @Inject constructor(private val repository: WalletRepository) {
    suspend operator fun invoke(newPassword: String, mnemonic: String) = repository.restoreWallet(newPassword, mnemonic)
}