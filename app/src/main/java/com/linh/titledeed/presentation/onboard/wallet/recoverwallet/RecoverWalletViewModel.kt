package com.linh.titledeed.presentation.onboard.wallet.recoverwallet

import androidx.annotation.StringRes
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
class RecoverWalletViewModel @Inject constructor(
    private val restoreWalletUseCase: RestoreWalletUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password
    private val _passwordError = MutableStateFlow<Int>(0)
    val passwordError: StateFlow<Int> get() = _passwordError

    private val _mnemonic = MutableStateFlow("")
    val mnemonic: StateFlow<String> get() = _mnemonic
    private val _mnemonicError = MutableStateFlow<Int>(0)
    val mnemonicError: StateFlow<Int> get() = _mnemonicError

    private val _isRecoverFromMnemonic = MutableStateFlow(true)
    val isRecoverFromMnemonic: StateFlow<Boolean> get() = _isRecoverFromMnemonic

    private val _privateKey = MutableStateFlow("")
    val privateKey: StateFlow<String> get() = _privateKey
    private val _privateKeyError = MutableStateFlow<Int>(0)
    val privateKeyError: StateFlow<Int> get() = _privateKeyError

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onMnemonicChange(mnemonic: String) {
        _mnemonic.value = mnemonic
    }

    fun onRecoverFromMnemonicChange(value: Boolean) {
        _isRecoverFromMnemonic.value = value
    }

    fun onPrivateKeyChange(privateKey: String) {
        _privateKey.value = privateKey
    }

    fun onClickSubmit() {
        viewModelScope.launch {
            var hasError = false

            if (isRecoverFromMnemonic.value) {
                if (mnemonic.value.isBlank()) {
                    _mnemonicError.value = R.string.error_mnemonic_blank
                    hasError = true
                }

                if (!hasError) {
                    restoreWalletUseCase(password.value, mnemonic.value)
                    navigationManager.navigate(NavigationDirections.main)
                }
            } else {
                if (privateKey.value.isBlank()) {
                    _privateKeyError.value = R.string.error_private_key_blank
                    hasError = true
                }

                if (!hasError) {
                    restoreWalletUseCase(privateKey.value)
                    navigationManager.navigate(NavigationDirections.main)
                }
            }
        }
    }
}