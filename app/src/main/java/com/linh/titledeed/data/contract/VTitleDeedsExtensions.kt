package com.linh.titledeed.data.contract

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthEstimateGas
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger
import java.util.*

class VTitleDeedsExtensions(credentials: Credentials, web3j: Web3j, contractGasProvider: ContractGasProvider): VTitleDeeds(ERC721_SMART_CONTRACT_ADDRESS, web3j, credentials, contractGasProvider) {
    fun estimateGasSafeTransferFrom(from: String, to: String, tokenId: BigInteger): Request<*, EthEstimateGas> {
        val function = Function(
            FUNC_safeTransferFrom,
            Arrays.asList<Type<*>>(
                Address(160, from),
                Address(160, to),
                Uint256(tokenId)
            ), emptyList()
        )

        return web3j.ethEstimateGas(
            Transaction.createFunctionCallTransaction(
                transactionManager.fromAddress,
                BigInteger("1"),
                gasProvider.getGasPrice(FUNC_safeTransferFrom),
                gasProvider.getGasLimit(FUNC_safeTransferFrom),
                contractAddress,
                executeRemoteCallTransaction(function).encodeFunctionCall()
            )
        )
    }

    fun estimateGasCreateSale(itemId: BigInteger, salePriceInWei: BigInteger, _metadataUri: String): Request<*, EthEstimateGas> {
        val function = Function(
            FUNC_OFFERFORSALE,
            Arrays.asList<Type<*>>(
                Uint256(itemId),
                Uint256(salePriceInWei),
                Utf8String(_metadataUri)
            ), emptyList()
        )
        return web3j.ethEstimateGas(
            Transaction.createFunctionCallTransaction(
                transactionManager.fromAddress,
                BigInteger("1"),
                gasProvider.getGasPrice(FUNC_OFFERFORSALE),
                gasProvider.getGasLimit(FUNC_OFFERFORSALE),
                contractAddress,
                executeRemoteCallTransaction(function).encodeFunctionCall()
            )
        )
    }

    fun estimateGasCancelSale(itemId: BigInteger): Request<*, EthEstimateGas> {
        val function = Function(
            FUNC_CLOSESALEOFFER,
            Arrays.asList<Type<*>>(Uint256(itemId)), emptyList()
        )
        return web3j.ethEstimateGas(
            Transaction.createFunctionCallTransaction(
                transactionManager.fromAddress,
                BigInteger("1"),
                gasProvider.getGasPrice(FUNC_CLOSESALEOFFER),
                gasProvider.getGasLimit(FUNC_CLOSESALEOFFER),
                contractAddress,
                executeRemoteCallTransaction(function).encodeFunctionCall()
            )
        )
    }

    fun estimateGasBuyDeed(itemId: BigInteger, value: BigInteger): Request<*, EthEstimateGas> {
        val function = Function(
            FUNC_BUYDEED,
            listOf<Type<*>>(Uint256(itemId)), emptyList()
        )

        return web3j.ethEstimateGas(
            Transaction.createFunctionCallTransaction(
                transactionManager.fromAddress,
                BigInteger("1"),
                gasProvider.getGasPrice(FUNC_BUYDEED),
                gasProvider.getGasLimit(FUNC_BUYDEED),
                contractAddress,
                executeRemoteCallTransaction(function, value).encodeFunctionCall()
            )
        )
    }

    fun buyDeed(itemId: BigInteger?, value: BigInteger): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_BUYDEED,
            listOf<Type<*>>(Uint256(itemId)), emptyList()
        )
        return executeRemoteCallTransaction(function, value)
    }

    companion object {
        private const val ERC721_SMART_CONTRACT_ADDRESS =
            "0x646472E1bD381112Be77911329e61e1844Db8AB1"
    }
}