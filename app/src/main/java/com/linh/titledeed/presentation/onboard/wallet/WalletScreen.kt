package com.linh.titledeed.presentation.onboard.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import androidx.compose.material.Text

@Composable
fun WalletScreen(
    onClickInput: () -> Unit, onClickCreate: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle(
            title = stringResource(R.string.wallet_screen_title),
            subtitle = stringResource(R.string.wallet_screen_description)
        )
        Button(onClickInput) {
            Text(stringResource(R.string.wallet_input))
        }
        Spacer(Modifier.height(8.dp))
        Button(onClickCreate) {
            Text(stringResource(R.string.wallet_create))
        }
    }
}

@Composable
@Preview
fun WalletScreenPreview() {
    WalletScreen(onClickInput = { /*TODO*/ }) {

    }
}