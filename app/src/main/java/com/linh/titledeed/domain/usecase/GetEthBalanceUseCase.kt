package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.WalletRepository
import javax.inject.Inject

class GetEthBalanceUseCase @Inject constructor(val walletRepository: WalletRepository) {
    suspend operator fun invoke(address: String) = walletRepository.getWalletBalance(address)
}