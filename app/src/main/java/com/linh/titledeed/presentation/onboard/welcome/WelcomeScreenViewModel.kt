package com.linh.titledeed.presentation.onboard.welcome

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val navigationManager: NavigationManager
): ViewModel() {
    fun onClickContinue() {
        navigationManager.navigate(NavigationDirections.wallet)
    }
}