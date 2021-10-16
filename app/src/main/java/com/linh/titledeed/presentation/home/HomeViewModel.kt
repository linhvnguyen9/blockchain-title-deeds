package com.linh.titledeed.presentation.home

import androidx.lifecycle.ViewModel
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getWalletInfoUseCase: GetWalletInfoUseCase): ViewModel() {
    private val _wallet = MutableStateFlow(Wallet("", "", "", ""))
    val wallet: StateFlow<Wallet> get() = _wallet

    init {
        getWallet()
    }

    private fun getWallet() {
        _wallet.value = getWalletInfoUseCase()
    }
}