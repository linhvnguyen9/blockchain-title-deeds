package com.linh.titledeed.presentation.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetEthBalanceUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.domain.usecase.LogoutWalletUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getWalletInfoUseCase: GetWalletInfoUseCase,
    private val getEthBalanceUseCase: GetEthBalanceUseCase,
    private val logoutWalletUseCase: LogoutWalletUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _wallet = MutableStateFlow(Wallet("", "", "", ""))
    val wallet: StateFlow<Wallet> get() = _wallet

    init {
        getWallet()
    }

    fun onClickLogout() {
        navigationManager.navigate(NavigationCommand(NavigationDirections.onboardWallet, NavigationDirections.main, true))
        logoutWalletUseCase()
    }

    private fun getWallet() {
        viewModelScope.launch {
            val wallet = getWalletInfoUseCase()
            val balance = getEthBalanceUseCase(wallet.address)
            _wallet.value = wallet.copy(balance = balance)
        }
    }
}