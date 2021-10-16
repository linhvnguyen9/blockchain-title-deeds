package com.linh.titledeed.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Text
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun HomeScreen(wallet: Wallet) {
    Column(screenModifier) {
        Text("Home Screen!")

        Text("Wallet address: ${wallet.address}")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

}