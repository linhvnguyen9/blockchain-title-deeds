package com.linh.titledeed.data.contract

import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction
import com.linh.titledeed.domain.entity.Wallet
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.gas.ContractGasProvider
import timber.log.Timber
import java.math.BigInteger
import javax.inject.Inject

class TitleDeedService @Inject constructor(private val web3j: Web3j) {
    private lateinit var smartContract: VTitleDeedsExtensions

    fun initService(wallet: Wallet) {
        val credentials = initCredentials(wallet)
        smartContract = initSmartContract(credentials)
    }

    private fun initCredentials(wallet: Wallet): Credentials {
        return WalletUtils.loadBip39Credentials(wallet.password, wallet.mnemonic)
    }

    private fun initSmartContract(credentials: Credentials): VTitleDeedsExtensions {
        val contractGasProvider = object :
            ContractGasProvider {
            override fun getGasPrice(contractFunc: String?): BigInteger {
                return web3j.ethGasPrice().send().gasPrice
            }

            override fun getGasPrice(): BigInteger {
                return web3j.ethGasPrice().send().gasPrice
            }

            override fun getGasLimit(contractFunc: String?): BigInteger {
                return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().block.gasLimit
            }

            override fun getGasLimit(): BigInteger {
                return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().block.gasLimit
            }
        }

        return VTitleDeedsExtensions(credentials, web3j, contractGasProvider)
    }

    suspend fun getBalance(owner: String): BigInteger =
        withContext(Dispatchers.IO) {
            return@withContext smartContract.balanceOf(owner).send()
        }

    suspend fun getTokensOfOwner(ownerAddress: String, index: BigInteger): BigInteger =
        withContext(Dispatchers.IO) {
            return@withContext smartContract.tokenOfOwnerByIndex(ownerAddress, index).send()
        }

    suspend fun getMetadataUri(tokenId: BigInteger): String = withContext(Dispatchers.IO) {
        return@withContext smartContract.tokenURI(tokenId).send()
    }

    fun getAllOwnedDeeds(): List<Deed> {
        web3j.replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST)
            .subscribeOn(Schedulers.io())
            .subscribe()

        return emptyList()
    }

    suspend fun estimateGasTransferOwnership(transaction: TransferOwnershipTransaction): String =
        withContext(Dispatchers.IO) {
            Timber.d("estimateGasTransferOwnership sender address ${transaction.senderAddress} receiverAddress ${transaction.receiverAddress}")
            Timber.d("estimateGasTransferOwnership tokenId ${transaction.tokenId.toBigDecimal()}")

            return@withContext smartContract.estimateGasSafeTransferFrom(transaction.senderAddress, transaction.receiverAddress, transaction.tokenId.toBigInteger()).send().amountUsed.toString(10)
        }

    suspend fun transferOwnership(transaction: TransferOwnershipTransaction): String =
        withContext(Dispatchers.IO) {
            try {
                val response = smartContract.safeTransferFrom(transaction.senderAddress, transaction.receiverAddress, transaction.tokenId.toBigInteger()).send()
                return@withContext ""
            } catch (e: Exception) {
                return@withContext e.message ?: ""
            }
        }

    companion object {
        private val ETH_DECIMALS = BigInteger("1000000000000000000")
    }
}