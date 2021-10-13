package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class GetWalletInfoUseCase @Inject constructor(private val walletRepository: WalletRepository) {
    operator fun invoke() = walletRepository.getWallet()
}