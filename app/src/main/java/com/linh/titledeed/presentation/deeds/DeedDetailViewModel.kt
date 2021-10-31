package com.linh.titledeed.presentation.deeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.usecase.GetDeedDetailUseCase
import com.linh.titledeed.domain.usecase.GetSaleInfoUseCase
import com.linh.titledeed.domain.usecase.GetTokenOwnerUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeedDetailViewModel @Inject constructor(private val getDeedDetailUseCase: GetDeedDetailUseCase, private val getSaleInfoUseCase: GetSaleInfoUseCase, private val getWalletInfoUseCase: GetWalletInfoUseCase, private val getTokenOwnerUseCase: GetTokenOwnerUseCase, private val navigationManager: NavigationManager): ViewModel() {
    private val _deed = MutableStateFlow(Deed("", "", "", "", 0.0, 0, false, LandPurpose.NON_AGRICULTURAL, 0, 0))
    val deed : StateFlow<Deed> get() = _deed

    private val _sale = MutableStateFlow(Sale("", "", "", "", emptyList(), "", "", false))
    val sale : StateFlow<Sale> get() = _sale

    private val _tokenOwner = MutableStateFlow("")
    val tokenOwner: StateFlow<String> get() = _tokenOwner
    private val _isOwner = MutableStateFlow(false)
    val isOwner: StateFlow<Boolean> get() = _isOwner

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    fun getDeed(tokenId: String) {
        viewModelScope.launch {
            _isRefreshing.value = true

            val wallet = getWalletInfoUseCase()

            _deed.value = getDeedDetailUseCase(tokenId)
            _sale.value = getSaleInfoUseCase(tokenId)
            _tokenOwner.value = getTokenOwnerUseCase(tokenId)
            _isOwner.value = wallet.address == tokenOwner.value

            _isRefreshing.value = false
        }
    }

    fun onClickTransfer() {
        navigationManager.navigate(NavigationDirections.TransferOwnershipNavigation.transferOwnership(deed.value.id))
    }

    fun onClickSell() {
        navigationManager.navigate(NavigationDirections.SellDeedNavigation.sellDeed(deed.value.id))
    }

    fun onClickCancelSell() {
        navigationManager.navigate(NavigationDirections.TransactionInfoNavigation.transactionInfo(TransactionType.CANCEL_SALE, "0x0", sale.value.tokenId, navigateBackDestination = NavigationDirections.DeedDetailNavigation.route, popInclusive = false))
    }
}