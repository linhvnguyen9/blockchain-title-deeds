package com.linh.titledeed.presentation.onboard.wallet

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val navigationManager: NavigationManager) : ViewModel() {
    fun onClickCreateWallet() {
        navigationManager.navigate(NavigationDirections.createWallet)
    }

    fun onClickInputWallet() {
        navigationManager.navigate(NavigationDirections.inputWallet)
    }
}