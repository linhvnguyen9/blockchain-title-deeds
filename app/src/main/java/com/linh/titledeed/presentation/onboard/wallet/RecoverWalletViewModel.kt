package com.linh.titledeed.presentation.onboard.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.usecase.RestoreWalletUseCase
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverWalletViewModel @Inject constructor(private val restoreWalletUseCase: RestoreWalletUseCase, private val navigationManager: NavigationManager) : ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password
    private val _passwordError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val passwordError: StateFlow<Int> get() = _passwordError

    private val _mnemonic = MutableStateFlow("")
    val mnemonic: StateFlow<String> get() = _mnemonic
    private val _mnemonicError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val mnemonicError: StateFlow<Int> get() = _mnemonicError

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onMnemonicChange(mnemonic: String) {
        _mnemonic.value = mnemonic
    }

    fun onClickSubmit() {
        viewModelScope.launch {
            var hasError = false

            if (password.value.isBlank()) {
                _passwordError.value = R.string.error_password_blank
                hasError = true
            } else if (password.value.length < 8) {
                _passwordError.value = R.string.error_password_too_short
                hasError = true
            }

            if (mnemonic.value.isBlank()) {
                _mnemonicError.value = R.string.error_mnemonic_blank
                hasError = true
            }

            if (!hasError) {
                restoreWalletUseCase(password.value, mnemonic.value)
                navigationManager.navigate(NavigationDirections.home)
            }
        }
    }
}