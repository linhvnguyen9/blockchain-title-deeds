package com.linh.titledeed.presentation.onboard.wallet

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class InputWalletViewModel @Inject constructor(private val navigationManager: NavigationManager) : ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _mnemonic = MutableStateFlow("")
    val mnemonic: StateFlow<String> get() = _mnemonic

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onMnemonicChange(mnemonic: String) {
        _mnemonic.value = mnemonic
    }

    fun onClickSubmit() {
        navigationManager.navigate(NavigationDirections.home)
    }
}