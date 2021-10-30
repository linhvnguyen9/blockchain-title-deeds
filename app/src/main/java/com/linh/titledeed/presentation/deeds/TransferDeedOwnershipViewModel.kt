package com.linh.titledeed.presentation.deeds

import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.internal.parseHexDigit
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransferDeedOwnershipViewModel @Inject constructor(
    private val getWalletInfoUseCase: GetWalletInfoUseCase,
    private val navigationManager: NavigationManager
) :
    ViewModel() {
    private val _receiverAddress = MutableStateFlow("")
    val receiverAddress: StateFlow<String> get() = _receiverAddress

    private val _receiverAddressError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val receiverAddressError: StateFlow<Int> get() = _receiverAddressError

    private lateinit var tokenId: String

    fun setReceiverAddress(address: String) {
        _receiverAddress.value = address
    }

    fun onClickSubmit() {
        val selfWalletAddress = getWalletInfoUseCase().address

        if (!isValidReceiverAddress(selfWalletAddress, receiverAddress.value)) {
            return
        }

        val navDirection = NavigationDirections.TransactionInfoNavigation.transactionInfo(
            TransactionType.TRANSFER_OWNERSHIP,
            receiverAddress.value,
            tokenId,
            "",
            "",
            NavigationDirections.ownedDeeds.destination,
            false
        )
        navigationManager.navigate(
            NavigationCommand(
                navDirection,
                NavigationDirections.ownedDeeds,
                true
            )
        )
    }

    fun setTokenId(tokenId: String) {
        this.tokenId = tokenId
    }

    private fun isValidReceiverAddress(senderAddress: String, receiverAddress: String): Boolean {
        return when {
            receiverAddress.isBlank() -> {
                _receiverAddressError.value = R.string.error_receiver_address_blank
                false
            }
            receiverAddress == senderAddress -> {
                _receiverAddressError.value = R.string.error_receiver_address_is_self
                false
            }
            !receiverAddress.matches("^(0x)?[0-9a-f]{40}".toRegex(RegexOption.IGNORE_CASE)) -> {
                _receiverAddressError.value = R.string.error_receiver_address_format
                false
            }
            else -> {
                true
            }
        }
    }
}