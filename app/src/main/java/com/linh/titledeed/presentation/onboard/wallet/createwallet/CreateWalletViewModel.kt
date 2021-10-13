package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.CreateWalletUseCase
import com.linh.titledeed.domain.usecase.SaveWalletUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val createWalletUseCase: CreateWalletUseCase,
    private val saveWalletUseCase: SaveWalletUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password
    private val _passwordError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val passwordError: StateFlow<Int> get() = _passwordError
    private val _confirmCreateWalletEnabled = MutableStateFlow("")
    val confirmCreateWalletEnabled: StateFlow<String> get() = _confirmCreateWalletEnabled

    private val _wallet = MutableStateFlow(Wallet("", "", "", ""))
    val wallet: StateFlow<Wallet> get() = _wallet
    private val _mnemonic = MutableStateFlow<List<String>>(emptyList())
    val mnemonic: StateFlow<List<String>> get() = _mnemonic

    private val _confirmMnemonicSelected = MutableStateFlow<List<String>>(emptyList())
    val confirmMnemonicSelected: StateFlow<List<String>> get() = _confirmMnemonicSelected
    private val _confirmMnemonicRemaining = MutableStateFlow<List<String>>(emptyList())
    val confirmMnemonicRemaining: StateFlow<List<String>> get() = _confirmMnemonicRemaining
    private val _confirmMnemonicError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val confirmMnemonicError: StateFlow<Int> get() = _confirmMnemonicError

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSubmitPassword() {
        var hasError = false
        if (password.value.isBlank()) {
            _passwordError.value = R.string.error_password_blank
            hasError = true
        } else if (password.value.length < 8) {
            _passwordError.value = R.string.error_password_too_short
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

    fun onAddConfirmMnemonicWord(word: String) {
        _confirmMnemonicSelected.value = confirmMnemonicSelected.value.toMutableList().apply { add(word) }
        _confirmMnemonicRemaining.value = confirmMnemonicRemaining.value.toMutableList().apply { remove(word) }
    }

    fun onRemoveConfirmMnemonicWord(word: String) {
        _confirmMnemonicSelected.value = confirmMnemonicSelected.value.toMutableList().apply { remove(word) }
        _confirmMnemonicRemaining.value = confirmMnemonicRemaining.value.toMutableList().apply { add(word) }
    }

    fun onConfirmViewedMnemonic() {
        navigationManager.navigate(NavigationDirections.confirmMnemonic)
        shuffleConfirmMnemonicWords()
    }

    fun onConfirmMnemonic() {
        if (confirmMnemonicSelected.value == mnemonic.value) {
            viewModelScope.launch {
                val navCommand = NavigationCommand(NavigationDirections.home, NavigationDirections.default, true)
                saveWalletUseCase(wallet.value)
                navigationManager.navigate(navCommand)
            }
        } else {
            _confirmMnemonicError.value = R.string.error_mnemonic_mismatch
        }
    }

    private fun shuffleConfirmMnemonicWords() {
        _confirmMnemonicRemaining.value = mnemonic.value.shuffled()
    }
}