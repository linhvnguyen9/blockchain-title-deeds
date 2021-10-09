package com.linh.titledeed.data.repository

import com.linh.titledeed.data.contract.WalletService
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(private val walletService: WalletService): WalletRepository {
    override suspend fun createWallet(password: String): Wallet = withContext(Dispatchers.IO) {
        return@withContext walletService.createWallet(password)
    }
}