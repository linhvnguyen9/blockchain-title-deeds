package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationCommand
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(private val navigationManager: NavigationManager): ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> get() = _confirmPassword

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun onSubmitPassword() {
        navigationManager.navigate(NavigationDirections.walletMnemonic)
    }

    fun onConfirmViewedMnemonic() {
        navigationManager.navigate(NavigationDirections.confirmMnemonic)
    }

    fun onConfirmMnemonic() {
        navigationManager.navigate(NavigationDirections.home)
    }
}