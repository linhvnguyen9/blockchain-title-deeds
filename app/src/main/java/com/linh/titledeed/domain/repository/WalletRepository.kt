package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.entity.Wallet
import java.math.BigInteger

interface WalletRepository {
    suspend fun createWallet(password: String): Wallet
    suspend fun restoreWallet(newPassword: String, mnemonic: String): Wallet
    suspend fun saveWallet(wallet: Wallet)
    fun getWallet() : Wallet
    fun logoutWallet()
    suspend fun getWalletBalance(address: String) : BigInteger
}