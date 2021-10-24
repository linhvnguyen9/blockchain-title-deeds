package com.linh.titledeed.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.entity.TransferOwnershipTransaction
import com.linh.titledeed.domain.usecase.EstimateTransactionGasUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionInfoViewModel @Inject constructor(private val getWalletInfoUseCase: GetWalletInfoUseCase, private val estimateTransactionGasUseCase: EstimateTransactionGasUseCase, private val navigationManager: NavigationManager): ViewModel() {
    private val _transaction = MutableStateFlow<Transaction?>(null)
    val transaction: StateFlow<Transaction?> get() = _transaction

    fun getTransactionDetail(transactionType: TransactionType, receiverAddress: String, tokenId: String = "") {
        viewModelScope.launch {
            val wallet = getWalletInfoUseCase()

            val transaction = when (transactionType) {
                TransactionType.TRANSFER_OWNERSHIP -> {
                    val tempTransaction = TransferOwnershipTransaction(senderAddress = wallet.address, receiverAddress = receiverAddress, tokenId = tokenId)
                    estimateTransactionGasUseCase(tempTransaction)
                }
            }
            _transaction.value = transaction
        }
    }

    fun onConfirm() {

    }

    fun onDismiss() {
        navigationManager.navigate(NavigationDirections.back)
    }
}