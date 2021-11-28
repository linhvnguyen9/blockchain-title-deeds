package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
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
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun CreateWalletScreen(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String,
    submitButtonEnabled: Boolean,
    onClickSubmit: () -> Unit
) {
    Column(screenModifier) {
        ScreenTitle(title = stringResource(R.string.create_wallet_screen_title), subtitle = "")

        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            Modifier.fillMaxWidth(),
            errorText = passwordError,
            label = {
                Text(stringResource(R.string.all_password))
            },
        )
        Text(stringResource(R.string.passwords_requirement))

        Spacer(Modifier.height(16.dp))

        Button(onClickSubmit, Modifier.align(Alignment.CenterHorizontally), enabled = submitButtonEnabled) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Preview
@Composable
fun CreateWalletScreenPreview() {
    CreateWalletScreen(
        password = "",
        onPasswordChange = {},
        "",
        true
    ) {

    }
}