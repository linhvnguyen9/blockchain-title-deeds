package com.linh.titledeed.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NamedNavArgument
import com.linh.titledeed.NavigationDirection
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.*
import com.linh.titledeed.domain.usecase.EstimateTransactionGasUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.domain.usecase.MakeTransactionUseCase
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionInfoViewModel @Inject constructor(
    private val getWalletInfoUseCase: GetWalletInfoUseCase,
    private val estimateTransactionGasUseCase: EstimateTransactionGasUseCase,
    private val makeTransactionUseCase: MakeTransactionUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _transaction = MutableStateFlow<Resource<Transaction>?>(null)
    val transaction: StateFlow<Resource<Transaction>?> get() = _transaction

    private val _transactionResponse = MutableStateFlow<Resource<Any>?>(null)
    val transactionResponse: StateFlow<Resource<Any>?> get() = _transactionResponse

    private var popUpToInclusive: Boolean = false
    private var popUpToRoute: String = ""

    fun getTransactionDetail(
        transactionType: TransactionType,
        receiverAddress: String,
        tokenId: String = "",
        priceInWei: String = "",
        metadataUri: String = "",
        navigateBackDestination: String = "",
        popInclusive: Boolean = false
    ) {
        viewModelScope.launch {
            popUpToRoute = navigateBackDestination
            popUpToInclusive = popInclusive
            val wallet = getWalletInfoUseCase()

            val transaction = when (transactionType) {
                TransactionType.TRANSFER_OWNERSHIP -> {
                    TransferOwnershipTransaction(
                        senderAddress = wallet.address,
                        receiverAddress = receiverAddress,
                        tokenId = tokenId
                    )
                }
                TransactionType.CREATE_SALE -> {
                    CreateSaleTransaction(
                        senderAddress = wallet.address,
                        tokenId = tokenId,
                        priceInWei = priceInWei,
                        metadataUri = metadataUri
                    )
                }
                TransactionType.CANCEL_SALE -> {
                    CancelSaleTransaction(
                        senderAddress = wallet.address,
                        tokenId = tokenId,
                    )
                }
            }
            val transactionInfoWithGasEstimate = estimateTransactionGasUseCase(transaction)
            _transaction.value = transactionInfoWithGasEstimate
        }
    }

    fun onConfirm() {
        viewModelScope.launch {
            _transactionResponse.value = Resource.loading()

            val transaction = transaction.value

            transaction?.data?.let {
                val response = makeTransactionUseCase(it)
                _transactionResponse.value = response
            }
        }
    }

    fun onDismiss(popUpTo: Boolean) {
        if (popUpTo) {
            val backDirection = object : NavigationDirection() {
                override val arguments: List<NamedNavArgument>
                    get() = emptyList()
                override val destination: String
                    get() = popUpToRoute
                override val isBottomNavigationItem: Boolean
                    get() = true
                override val screenNameRes: Int
                    get() = R.string.app_name
            }
            navigationManager.navigate(NavigationCommand(NavigationDirections.back, backDirection, popUpToInclusive))
        } else {
            navigationManager.navigate(NavigationDirections.back)
        }
    }
}