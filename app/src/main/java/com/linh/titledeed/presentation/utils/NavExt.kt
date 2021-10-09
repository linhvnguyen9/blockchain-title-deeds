package com.linh.titledeed.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.linh.titledeed.presentation.onboard.wallet.createwallet.CreateWalletViewModel

@Composable
inline fun <reified VM : ViewModel> parentViewModel(
    navController: NavController,
    parentRoute: String
): VM {
    val parentEntry = remember { navController.getBackStackEntry(parentRoute) }
    return hiltViewModel(parentEntry)
}