package com.linh.titledeed.data.repository

import com.linh.titledeed.data.contract.WalletService
import com.linh.titledeed.data.local.EncryptedSharedPreference
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(private val walletService: WalletService): WalletRepository {
    override suspend fun createWallet(password: String): Wallet = withContext(Dispatchers.IO) {
        return@withContext walletService.createWallet(password)
    }

    override suspend fun restoreWallet(newPassword: String, mnemonic: String): Wallet = withContext(Dispatchers.IO) {
        return@withContext walletService.restoreWalletFromMnemonic(newPassword, mnemonic)
    }

    override suspend fun restoreWallet(privateKey: String): Wallet = withContext(Dispatchers.IO) {
        return@withContext walletService.restoreWalletFromPrivateKey(privateKey)
    }

    override suspend fun saveWallet(wallet: Wallet) = withContext(Dispatchers.IO) {
        wallet.run {
            EncryptedSharedPreference.saveWalletAddress(address)
            EncryptedSharedPreference.saveWalletMnemonic(mnemonic)
            EncryptedSharedPreference.saveWalletPassword(password)
            EncryptedSharedPreference.saveWalletPrivateKey(privateKey)
        }
    }

    override fun getWallet(): Wallet {
        val address = EncryptedSharedPreference.getWalletAddress()
        val mnemonic = EncryptedSharedPreference.getWalletMnemonic()
        val privateKey = EncryptedSharedPreference.getWalletPrivateKey()
        val password = EncryptedSharedPreference.getWalletPassword()

        return Wallet(password, mnemonic, privateKey, address)
    }

    override fun logoutWallet() {
        EncryptedSharedPreference.clearWalletData()
    }

    override suspend fun getWalletBalance(address: String): BigInteger {
        return walletService.getEthBalance(address)
    }
}