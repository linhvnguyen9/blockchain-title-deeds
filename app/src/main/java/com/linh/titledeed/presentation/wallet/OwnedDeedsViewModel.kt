package com.linh.titledeed.presentation.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetAllOwnedDeedsUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OwnedDeedsViewModel @Inject constructor(private val getWalletInfoUseCase: GetWalletInfoUseCase, private val getAllOwnedDeedsUseCase: GetAllOwnedDeedsUseCase, private val navigationManager: NavigationManager): ViewModel() {
    private val _wallet = MutableStateFlow(Wallet("", "", "", ""))
    val wallet: StateFlow<Wallet> get() = _wallet

    private val _deeds = MutableStateFlow<List<Deed>?>(null)
    val deeds : StateFlow<List<Deed>?> get() = _deeds

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing : StateFlow<Boolean> get() = _isRefreshing

    init {
        viewModelScope.launch {
            getDeeds()
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            getDeeds()
            _isRefreshing.value = false
        }
    }

    fun onClickDeed(deed: Deed) {
        viewModelScope.launch {
            Timber.d("onClickDeed id ${deed.id}")
            navigationManager.navigate(NavigationCommand(NavigationDirections.DeedDetailNavigation.detail(deed.id)))
        }
    }

    private suspend fun getDeeds() {
        val wallet = getWalletInfoUseCase()
        _deeds.value = getAllOwnedDeedsUseCase(wallet.address)
    }
}