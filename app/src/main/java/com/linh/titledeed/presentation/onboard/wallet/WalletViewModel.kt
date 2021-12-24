package com.linh.titledeed.presentation.onboard.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val navigationManager: NavigationManager) : ViewModel() {
    fun onClickCreateWallet() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationDirections.createWallet)
        }
    }

    fun onClickInputWallet() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationDirections.inputWallet)
        }
    }
}