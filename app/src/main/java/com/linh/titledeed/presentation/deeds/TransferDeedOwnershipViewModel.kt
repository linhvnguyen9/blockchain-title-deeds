package com.linh.titledeed.presentation.deeds

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransferDeedOwnershipViewModel @Inject constructor(private val navigationManager: NavigationManager) :
    ViewModel() {
    private val _receiverAddress = MutableStateFlow("")
    val receiverAddress: StateFlow<String> get() = _receiverAddress

    private lateinit var tokenId: String

    fun setReceiverAddress(address: String) {
        _receiverAddress.value = address
    }

    fun onClickSubmit() {
        //TODO: Check for empty string
        //TODO: Check for receiver address that is the same as the owner
        //TODO: Check for receiver address that is too short / invalid

        Timber.d("onClickSubmit receiverAddress ${receiverAddress.value}")
        navigationManager.navigate(
            NavigationDirections.TransactionInfoNavigation.transactionInfo(
                TransactionType.TRANSFER_OWNERSHIP, receiverAddress.value, tokenId
            )
        )
    }

    fun setTokenId(tokenId: String) {
        this.tokenId = tokenId
    }
}