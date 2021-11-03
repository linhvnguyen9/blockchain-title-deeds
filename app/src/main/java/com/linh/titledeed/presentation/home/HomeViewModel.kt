package com.linh.titledeed.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetAllSalesUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase,
    private val getWalletInfoUseCase: GetWalletInfoUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _wallet = MutableStateFlow(Wallet("", "", "", ""))
    val wallet: StateFlow<Wallet> get() = _wallet

    private val _sales = MutableStateFlow<List<Sale>>(emptyList())
    val sales: StateFlow<List<Sale>> get() = _sales

    init {
        getWallet()
        getSales()
    }

    fun onClickSale(sale: Sale) {
        navigationManager.navigate(NavigationDirections.DeedDetailNavigation.detail(sale.tokenId))
    }

    private fun getWallet() {
        _wallet.value = getWalletInfoUseCase()
    }

    private fun getSales() {
        viewModelScope.launch {
            val response = getAllSalesUseCase()
            Timber.d("HomeViewModel response $response")
            _sales.value = response
        }
    }
}