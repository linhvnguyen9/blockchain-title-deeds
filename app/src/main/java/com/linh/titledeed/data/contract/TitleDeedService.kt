package com.linh.titledeed.data.contract

import android.app.Application
import com.linh.titledeed.domain.entity.Wallet
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger
import javax.inject.Inject

class TitleDeedService(wallet: Wallet) {
    @Inject
    lateinit var web3j: Web3j
    @Inject
    lateinit var application: Application

    private val smartContract: VTitleDeeds

    init {
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

    companion object {
        private const val ERC721_SMART_CONTRACT_ADDRESS =
            "0x6aec55c34fcd7f874237becb83e2a2671caa06b9"

        private val ETH_DECIMALS = BigInteger("1000000000000000000")
    }
}