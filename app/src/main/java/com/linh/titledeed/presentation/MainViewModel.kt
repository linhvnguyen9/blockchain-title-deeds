package com.linh.titledeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val navigationManager: NavigationManager): ViewModel() {
    fun onClickBottomNavigationHome() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationDirections.home)
        }
    }

    fun onClickBottomNavigationWallet() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationDirections.wallet)
        }
    }
}