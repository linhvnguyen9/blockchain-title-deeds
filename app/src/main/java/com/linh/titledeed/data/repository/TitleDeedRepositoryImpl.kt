package com.linh.titledeed.data.repository

import com.linh.titledeed.data.contract.TitleDeedService
import com.linh.titledeed.data.local.EncryptedSharedPreference
import com.linh.titledeed.data.remote.IpfsService
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.repository.TitleDeedRepository
import timber.log.Timber
import javax.inject.Inject

class TitleDeedRepositoryImpl @Inject constructor(private val ipfsService: IpfsService, private val titleDeedService: TitleDeedService): TitleDeedRepository {
    init {
        val wallet = loadWalletInfo()
        titleDeedService.initService(wallet)
    }

    override suspend fun getAllOwnedDeeds(address: String): List<Deed> {
        val deeds = mutableListOf<Deed>()

        val totalCount = titleDeedService.getBalance(address).toLong()
        Timber.d("getAllOwnedDeeds count $totalCount")

        for (i in 0 until totalCount) {
            val tokenId = titleDeedService.getTokensOfOwner(address, i.toBigInteger())
            val metadataIpfsUri = titleDeedService.getMetadataUri(tokenId)
            val cid = metadataIpfsUri.replace("ipfs://", "")
            val gatewayUrl = "https://gateway.pinata.cloud/ipfs/$cid"
            val metadata = ipfsService.getMetadataFile(gatewayUrl)
            val deed = metadata.toDomainModel()
            deeds.add(deed)
        }

        return deeds
    }

    private fun loadWalletInfo(): Wallet {
        val address = EncryptedSharedPreference.getWalletAddress()
        val mnemonic = EncryptedSharedPreference.getWalletMnemonic()
        val privateKey = EncryptedSharedPreference.getWalletPrivateKey()
        val password = EncryptedSharedPreference.getWalletPassword()

        return Wallet(password, mnemonic, privateKey, address)
    }
}