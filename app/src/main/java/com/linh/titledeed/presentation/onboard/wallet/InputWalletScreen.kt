package com.linh.titledeed.presentation.onboard.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.composable.Text
import com.linh.titledeed.presentation.ui.theme.screenPadding

@Composable
fun InputWalletScreen(
    password: String,
    onPasswordChange: (String) -> Unit,
    mnemonic: String,
    onMnemonicChange: (String) -> Unit,
    onClickSubmit: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(screenPadding)) {
        ScreenTitle(
            title = "Input wallet",
            subtitle = stringResource(R.string.wallet_screen_description)
        )

        Text(stringResource(R.string.wallet_password))
        TextField(value = password, onValueChange = onPasswordChange, Modifier.fillMaxWidth())

        Spacer(Modifier.height(8.dp))

        Text(stringResource(R.string.wallet_mnemonic))
        TextField(value = mnemonic, onValueChange = onMnemonicChange, Modifier.fillMaxWidth())
        Text("Mnemonic is a set of words generated when you create a wallet (e.g: ripple scissors kick mammal...)")

        Spacer(Modifier.height(32.dp))

        Button(onClickSubmit, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Preview
@Composable
fun InputWalletScreenPreview() {
    InputWalletScreen("", {}, "", {}) {

    }
}