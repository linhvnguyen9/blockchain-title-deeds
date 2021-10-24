package com.linh.titledeed.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NamedNavArgument
import com.linh.titledeed.NavigationDirection
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction
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
    private val _transaction = MutableStateFlow<Transaction?>(null)
    val transaction: StateFlow<Transaction?> get() = _transaction

    private val _transactionResponse = MutableStateFlow<Resource<String>?>(null)
    val transactionResponse: StateFlow<Resource<String>?> get() = _transactionResponse

    private var popUpToInclusive: Boolean = false
    private var popUpToRoute: String = ""

    fun getTransactionDetail(
        transactionType: TransactionType,
        receiverAddress: String,
        tokenId: String = "",
        navigateBackDestination: String = "",
        popInclusive: Boolean = false
    ) {
        viewModelScope.launch {
            popUpToRoute = navigateBackDestination
            popUpToInclusive = popInclusive
            val wallet = getWalletInfoUseCase()

            val transaction = when (transactionType) {
                TransactionType.TRANSFER_OWNERSHIP -> {
                    val tempTransaction = TransferOwnershipTransaction(
                        senderAddress = wallet.address,
                        receiverAddress = receiverAddress,
                        tokenId = tokenId
                    )
                    estimateTransactionGasUseCase(tempTransaction)
                }
            }
            _transaction.value = transaction
        }
    }

    fun onConfirm() {
        viewModelScope.launch {
            _transactionResponse.value = Resource.loading()

            val transaction = transaction.value

            val response = when (transaction) {
                is TransferOwnershipTransaction -> {
                    makeTransactionUseCase(transaction)
                }
                else -> {
                    ""
                }
            }
            _transactionResponse.value = if (response.isBlank()) Resource.success("") else Resource.error(Exception(), response)
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