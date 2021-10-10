package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linh.titledeed.NavigationCommand
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.usecase.CreateWalletUseCase
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(private val createWalletUseCase: CreateWalletUseCase, private val navigationManager: NavigationManager): ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password
    private val _passwordError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val passwordError: StateFlow<Int> get() = _passwordError

    private val _mnemonic = MutableStateFlow<List<String>>(emptyList())
    val mnemonic: StateFlow<List<String>> get() = _mnemonic

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSubmitPassword() {
        var hasError = false
        if (password.value.isBlank()) {
            _passwordError.value = R.string.error_password_blank
            hasError = true
        }

        if (!hasError) {
            viewModelScope.launch {
                val wallet = createWalletUseCase(password.value)
                _mnemonic.value = wallet.mnemonic.split(" ")

                navigationManager.navigate(NavigationDirections.walletMnemonic)
            }
        }
    }

    fun onConfirmViewedMnemonic() {
        navigationManager.navigate(NavigationDirections.confirmMnemonic)
    }

    fun onConfirmMnemonic() {
        navigationManager.navigate(NavigationDirections.home)
    }
}