package com.linh.titledeed.presentation.onboard.welcome

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    getWalletInfoUseCase: GetWalletInfoUseCase,
    private val navigationManager: NavigationManager
): ViewModel() {

    init {
        val wallet = getWalletInfoUseCase()
        if (wallet.address.isNotBlank()) {
            navigationManager.navigate(NavigationCommand(NavigationDirections.main, NavigationDirections.default, true))
        }
    }

    fun onClickContinue() {
        navigationManager.navigate(NavigationDirections.onboardWallet)
    }
}