package com.linh.titledeed.presentation.onboard.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    getWalletInfoUseCase: GetWalletInfoUseCase,
    private val navigationManager: NavigationManager
): ViewModel() {

    init {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DURATION_MILLIS)
            val wallet = getWalletInfoUseCase()
            if (wallet.address.isNotBlank()) {
                navigationManager.navigate(NavigationCommand(NavigationDirections.main, NavigationDirections.default, true))
            } else {
                navigationManager.navigate(NavigationCommand(NavigationDirections.onboardWallet, NavigationDirections.default, true))
            }
        }
    }

    fun onClickContinue() {
        navigationManager.navigate(NavigationDirections.onboardWallet)
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION_MILLIS = 2000L
    }
}