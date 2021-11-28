package com.linh.titledeed.presentation.onboard.wallet.recoverwallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.PasswordTextField
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import androidx.compose.material.Text
import com.linh.titledeed.presentation.ui.theme.screenPadding

@Composable
fun InputWalletScreen(
    password: String,
    passwordError: String,
    onPasswordChange: (String) -> Unit,
    mnemonic: String,
    mnemonicError: String,
    onMnemonicChange: (String) -> Unit,
    onClickSubmit: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(screenPadding)) {
        ScreenTitle(
            title = stringResource(R.string.recover_wallet_screen_title),
            subtitle = stringResource(R.string.recover_wallet_screen_subtitle)
        )

        Text(stringResource(R.string.wallet_mnemonic))
        TextField(value = mnemonic, onValueChange = onMnemonicChange, Modifier.fillMaxWidth())
        if (mnemonicError.isNotBlank()) {
            Text(mnemonicError, color = MaterialTheme.colors.error)
        }
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.mnemonic_description))

        Spacer(Modifier.height(16.dp))

        Text(stringResource(R.string.wallet_password))
        PasswordTextField(value = password, onValueChange = onPasswordChange, Modifier.fillMaxWidth(), errorText = passwordError)

        Spacer(Modifier.height(32.dp))

        Button(onClickSubmit, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Preview
@Composable
fun InputWalletScreenPreview() {
    InputWalletScreen("", "", {}, "", "", {}) {

    }
}