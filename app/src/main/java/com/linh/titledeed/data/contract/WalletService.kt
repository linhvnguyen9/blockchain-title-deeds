package com.linh.titledeed.data.contract

import android.app.Application
import com.linh.titledeed.domain.entity.Wallet
import org.web3j.crypto.Bip39Wallet
import org.web3j.crypto.WalletUtils
import timber.log.Timber

class WalletService(private val application: Application) {
    fun createWallet(password: String): Wallet {
        val rawWallet = WalletUtils.generateBip39Wallet(password, application.filesDir)
        return generateWallet(password, rawWallet)
    }

    fun restoreWalletFromMnemonic(password: String, mnemonic: String): Wallet {
        val rawWallet = WalletUtils.generateBip39WalletFromMnemonic(password, mnemonic, application.filesDir)
        return generateWallet(password, rawWallet)
    }

    private fun generateWallet(password: String, rawWallet: Bip39Wallet): Wallet {
        val credential = WalletUtils.loadBip39Credentials(password, rawWallet.mnemonic)
        val privateKey = credential.ecKeyPair.privateKey.toString(16)
        val address = credential.address
        Timber.d("generateWallet mnemonic: ${rawWallet.mnemonic}")
        Timber.d("generateWallet privateKey: $privateKey")
        Timber.d("generateWallet address: $address")

        return Wallet(password, rawWallet.mnemonic, privateKey, address)
    }
}