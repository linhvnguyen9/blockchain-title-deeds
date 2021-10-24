package com.linh.titledeed.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionInfoViewModel @Inject constructor(private val getWalletInfoUseCase: GetWalletInfoUseCase, private val navigationManager: NavigationManager): ViewModel() {
    private val _transaction = MutableStateFlow<Transaction?>(null)
    val transaction: StateFlow<Transaction?> get() = _transaction

    fun getTransactionDetail(transactionType: TransactionType, receiverAddress: String) {
        viewModelScope.launch {
            val wallet = getWalletInfoUseCase()

            val transaction = when (transactionType) {
                TransactionType.TRANSFER_OWNERSHIP -> {
                    Transaction(transactionType, "", wallet.address, receiverAddress)
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