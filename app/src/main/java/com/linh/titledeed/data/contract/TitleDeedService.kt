package com.linh.titledeed.data.contract

import android.app.Application
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.Wallet
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger
import javax.inject.Inject

class TitleDeedService @Inject constructor(application: Application, private val web3j: Web3j) {
    private lateinit var smartContract: VTitleDeeds

    fun initService(wallet: Wallet) {
        val credentials = initCredentials(wallet)
        smartContract = initSmartContract(credentials)
    }

    private fun initCredentials(wallet: Wallet): Credentials{
        return WalletUtils.loadBip39Credentials(wallet.password, wallet.mnemonic)
    }

    private fun initSmartContract(credentials: Credentials): VTitleDeeds {
        return VTitleDeeds.load(
            ERC721_SMART_CONTRACT_ADDRESS, web3j, credentials, object :
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
            })
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

    companion object {
        private const val ERC721_SMART_CONTRACT_ADDRESS =
            "0xf359798202654c90c9fabba17E36ec2B8AA65Be1"

        private val ETH_DECIMALS = BigInteger("1000000000000000000")
    }
}