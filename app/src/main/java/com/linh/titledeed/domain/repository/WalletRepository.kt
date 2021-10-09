package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.entity.Wallet

interface WalletRepository {
    suspend fun createWallet(password: String): Wallet
}