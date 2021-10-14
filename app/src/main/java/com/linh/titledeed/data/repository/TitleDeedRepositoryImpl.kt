package com.linh.titledeed.data.repository

import com.linh.titledeed.data.contract.TitleDeedService
import com.linh.titledeed.data.local.EncryptedSharedPreference
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.repository.TitleDeedRepository

class TitleDeedRepositoryImpl: TitleDeedRepository {
    private val titleDeedService: TitleDeedService

    init {
        val wallet = loadWalletInfo()
        titleDeedService = TitleDeedService(wallet)
    }

    override fun getAllTokens() {

    }

    private fun loadWalletInfo(): Wallet {
        val address = EncryptedSharedPreference.getWalletAddress()
        val mnemonic = EncryptedSharedPreference.getWalletMnemonic()
        val privateKey = EncryptedSharedPreference.getWalletPrivateKey()
        val password = EncryptedSharedPreference.getWalletPassword()

        return Wallet(password, mnemonic, privateKey, address)
    }
}