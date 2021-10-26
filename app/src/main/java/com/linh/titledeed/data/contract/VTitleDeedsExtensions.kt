package com.linh.titledeed.data.contract

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthEstimateGas
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

    companion object {
        private const val ERC721_SMART_CONTRACT_ADDRESS =
            "0xFf652ECa31989c505783275C959D0369923c890C"
    }
}