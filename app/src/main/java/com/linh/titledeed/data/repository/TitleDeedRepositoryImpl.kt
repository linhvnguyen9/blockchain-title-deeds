package com.linh.titledeed.data.repository

import com.linh.titledeed.data.contract.TitleDeedService
import com.linh.titledeed.data.entity.DeedMetadataResponse
import com.linh.titledeed.data.entity.UploadSaleMetadataRequest
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

    override suspend fun getTokenOwner(tokenId: String): String {
        return titleDeedService.getTokenOwner(tokenId)
    }

    override suspend fun uploadSaleMetadata(sale: Sale): Resource<String> {
        return try {
            sale.run {
                val response = ipfsService.pinSaleMetadataToIpfs(UploadSaleMetadataRequest(tokenId, title, description, phoneNumber, imageUrls))
                Resource.success(response.cid)
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.error(e)
        }
    }

    override suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): Resource<TransferOwnershipTransaction> {
        try {
            val gasPrice = titleDeedService.estimateGasTransferOwnership(transaction)

            transaction.apply {
                return Resource.success(TransferOwnershipTransaction(gasPrice, senderAddress, receiverAddress, tokenId))
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Resource.error(e)
        }
    }

    override suspend fun transferOwnership(transaction: TransferOwnershipTransaction): Resource<Any> {
        return try {
            titleDeedService.transferOwnership(transaction)
            Resource.success("")
        } catch (e: Exception) {
            Timber.e(e)
            Resource.error(e)
        }
    }

    override suspend fun estimateGasCreateSale(transaction: CreateSaleTransaction): Resource<CreateSaleTransaction> {
        try {
            val gasPrice = titleDeedService.estimateGasCreateSale(transaction)

            transaction.run {
                return Resource.success(CreateSaleTransaction(gasPrice, senderAddress, tokenId, priceInWei, metadataUri))
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Resource.error(e)
        }
    }

    override suspend fun createSale(transaction: CreateSaleTransaction): Resource<Any> {
        return try {
            titleDeedService.createSale(transaction)
            Resource.success("")
        } catch (e: Exception) {
            Timber.e(e)
            Resource.error(e)
        }
    }

    override suspend fun estimateGasCancelSale(transaction: CancelSaleTransaction): Resource<CancelSaleTransaction> {
        try {
            val gasPrice = titleDeedService.estimateGasCancelSale(transaction)

            transaction.run {
                return Resource.success(CancelSaleTransaction(gasPrice, senderAddress, tokenId))
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Resource.error(e)
        }
    }

    override suspend fun cancelSale(transaction: CancelSaleTransaction): Resource<Any> {
        return try {
            titleDeedService.cancelSale(transaction)
            Resource.success("")
        } catch (e: Exception) {
            Timber.e(e)
            Resource.error(e)
        }
    }

    override suspend fun getAllSales(): List<Sale> {
        val sales = mutableListOf<Sale>()

        val totalSupply = titleDeedService.getTotalSupply().toLong()
        Timber.d("getAllSales count $totalSupply")

        for (i in 1 until totalSupply) {
            val tokenId = titleDeedService.getTokenIdByIndex(i.toBigInteger())
            Timber.d("getAllSales tokenId $tokenId")

            val sale = getSaleInfo(tokenId.toString(10))
            val deed = getDeedDetail(tokenId.toString(10))

            Timber.d("getAllSales sale $sale")

//            if (sale.isForSale) {
                sales.add(sale.copy(deed = deed))
//            }
        }

        return sales
    }

    override suspend fun getSaleInfo(tokenId: String): Sale {
        val saleDetail = titleDeedService.getSaleDetail(tokenId)
        Timber.d("getSaleInfo tokenId $tokenId saleDetail $saleDetail")
        val (sellerAddress, itemId, price, isForSale, metadataUri) = saleDetail
        return if (metadataUri.isNotBlank()) {
            val metadataUrl = getHttpLinkFromIpfsUri(metadataUri)
            val saleMetadata = ipfsService.getSaleMetadata(metadataUrl)
            val (_, title, description, phoneNumber, imageUrls) = saleMetadata

            Timber.d("getSaleInfo tokenId $tokenId saleMetadata $saleMetadata")

            Sale(itemId, title, description, phoneNumber, imageUrls, sellerAddress, price, isForSale)
        } else {
            Sale("", "", "", "", emptyList(), "", "", false)
        }
    }

    private suspend fun getTokenMetadata(tokenId: BigInteger): DeedMetadataResponse {
        val metadataIpfsUri = titleDeedService.getMetadataUri(tokenId)
        val gatewayUrl = getHttpLinkFromIpfsUri(metadataIpfsUri)
        return ipfsService.getDeedMetadata(gatewayUrl)
    }

    private fun loadWalletInfo(): Wallet {
        val address = EncryptedSharedPreference.getWalletAddress()
        val mnemonic = EncryptedSharedPreference.getWalletMnemonic()
        val privateKey = EncryptedSharedPreference.getWalletPrivateKey()
        val password = EncryptedSharedPreference.getWalletPassword()

        return Wallet(password, mnemonic, privateKey, address)
    }
}