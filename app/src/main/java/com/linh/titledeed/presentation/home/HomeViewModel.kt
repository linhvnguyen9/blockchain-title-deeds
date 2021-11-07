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
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _sales = MutableStateFlow<List<Sale>>(emptyList())
    val sales: StateFlow<List<Sale>> get() = _sales

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    init {
        getSales()
    }

    fun onClickSale(sale: Sale) {
        navigationManager.navigate(NavigationDirections.DeedDetailNavigation.detail(sale.tokenId))
    }

    fun onRefresh() {
        getSales()
    }

    private fun getSales() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val response = getAllSalesUseCase()
            Timber.d("HomeViewModel response $response")
            _sales.value = response
            _isRefreshing.value = false
        }
    }
}