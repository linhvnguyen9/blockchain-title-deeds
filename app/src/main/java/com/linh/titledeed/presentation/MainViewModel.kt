package com.linh.titledeed.presentation

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val navigationManager: NavigationManager): ViewModel() {
    fun onClickBottomNavigationHome() {
        navigationManager.navigate(NavigationDirections.home)
    }

    fun onClickBottomNavigationWallet() {
        navigationManager.navigate(NavigationDirections.wallet)
    }
}