package com.linh.titledeed.data.repository

import com.linh.titledeed.data.contract.TitleDeedService
import com.linh.titledeed.data.entity.DeedMetadataResponse
import com.linh.titledeed.data.entity.TokenOwnerException
import com.linh.titledeed.data.local.EncryptedSharedPreference
import com.linh.titledeed.data.remote.IpfsService
import com.linh.titledeed.data.utils.getHttpLinkFromIpfsUri
import com.linh.titledeed.domain.entity.*
import com.linh.titledeed.domain.repository.TitleDeedRepository
import com.linh.titledeed.domain.utils.Resource
import timber.log.Timber
import java.lang.Exception
import java.math.BigInteger
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
            Timber.d("getAllOwnedDeeds() tokenId $tokenId")

            val metadata = getTokenMetadata(tokenId)
            val deed = metadata.toDomainModel(tokenId.toString())
            deeds.add(deed)
        }

        return deeds
    }

    override suspend fun getDeedDetail(tokenId: String): Deed {
        Timber.d("getDeedDetail() tokenId $tokenId")
        return getTokenMetadata(tokenId.toBigInteger()).toDomainModel(tokenId)
    }

    override suspend fun uploadSaleMetadata(sale: Sale): Resource<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): Resource<TransferOwnershipTransaction> {
        try {
            val gasPrice = titleDeedService.estimateGasTransferOwnership(transaction)

            transaction.apply {
                return Resource.success(TransferOwnershipTransaction(gasPrice, senderAddress, receiverAddress, tokenId))
            }
        } catch (e: Exception) {
            return Resource.error(e)
        }
    }

    override suspend fun transferOwnership(transaction: TransferOwnershipTransaction): Resource<Any> {
        return try {
            titleDeedService.transferOwnership(transaction)
            Resource.success("")
        } catch (e: Exception) {
            Resource.error(e)
        }
    }

    private suspend fun getTokenMetadata(tokenId: BigInteger): DeedMetadataResponse {
        val metadataIpfsUri = titleDeedService.getMetadataUri(tokenId)
        val gatewayUrl = getHttpLinkFromIpfsUri(metadataIpfsUri)
        return ipfsService.getMetadataFile(gatewayUrl)
    }

    private fun loadWalletInfo(): Wallet {
        val address = EncryptedSharedPreference.getWalletAddress()
        val mnemonic = EncryptedSharedPreference.getWalletMnemonic()
        val privateKey = EncryptedSharedPreference.getWalletPrivateKey()
        val password = EncryptedSharedPreference.getWalletPassword()

        return Wallet(password, mnemonic, privateKey, address)
    }
}