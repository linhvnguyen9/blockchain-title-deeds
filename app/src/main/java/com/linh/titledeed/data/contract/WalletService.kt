package com.linh.titledeed.data.contract

import android.app.Application
import com.linh.titledeed.domain.entity.Wallet
import org.web3j.crypto.WalletUtils

class WalletService(private val application: Application) {
    suspend fun createWallet(password: String): Wallet {
        val rawWallet = WalletUtils.generateBip39Wallet(password, application.filesDir)
        return Wallet(password, rawWallet.mnemonic)
    }
}