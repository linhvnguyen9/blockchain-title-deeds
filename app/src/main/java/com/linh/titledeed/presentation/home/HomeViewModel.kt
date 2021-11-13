package com.linh.titledeed.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetAllSalesUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _residentialFilter = MutableStateFlow(true)
    val residentialFilter: StateFlow<Boolean> get() = _residentialFilter

    private val _nonResidentialFilter = MutableStateFlow(true)
    val nonResidentialFilter: StateFlow<Boolean> get() = _nonResidentialFilter

    private val _agriculturalFilter = MutableStateFlow(true)
    val agriculturalFilter: StateFlow<Boolean> get() = _agriculturalFilter

    private val _sales = MutableStateFlow<List<Sale>>(emptyList())
    val sales = _sales.combine(searchQuery) { sales, query ->
        sales.filter { it.deed != null && it.deed.address.contains(query) }
    }.combine(residentialFilter) {
        sales, residential ->
        sales.filter { it.deed != null && residential && it.deed.purpose == LandPurpose.RESIDENTIAL }
    }

    init {
        getSales()
    }

    fun onClickSale(sale: Sale) {
        navigationManager.navigate(NavigationDirections.DeedDetailNavigation.detail(sale.tokenId))
    }

    fun onRefresh() {
        getSales()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSetResidentialFilter(set: Boolean) {
        _residentialFilter.value = set
    }

    fun onSetNonResidentialFilter(set: Boolean) {
        _nonResidentialFilter.value = set
    }

    fun onSetAgriculturalFilter(set: Boolean) {
        _agriculturalFilter.value = set
    }

    private fun getSales() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val response = getAllSalesUseCase()
            Timber.d("HomeViewModel response $response")
            _sales.value = response
            _searchQuery.value = " "
            _isRefreshing.value = false
        }
    }
}