package com.linh.titledeed.data.contract

import android.app.Application
import com.linh.titledeed.domain.entity.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Bip39Wallet
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import timber.log.Timber
import java.math.BigInteger
import javax.inject.Inject

class WalletService @Inject constructor(private val web3j: Web3j, private val application: Application) {
    fun createWallet(password: String): Wallet {
        val rawWallet = WalletUtils.generateBip39Wallet(password, application.filesDir)
        return generateWallet(password, rawWallet)
    }

    fun restoreWalletFromMnemonic(password: String, mnemonic: String): Wallet {
        val rawWallet = WalletUtils.generateBip39WalletFromMnemonic(password, mnemonic, application.filesDir)
        return generateWallet(password, rawWallet)
    }

    suspend fun getEthBalance(address: String): BigInteger = withContext(Dispatchers.IO) {
        return@withContext web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
            .send()
            .balance
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