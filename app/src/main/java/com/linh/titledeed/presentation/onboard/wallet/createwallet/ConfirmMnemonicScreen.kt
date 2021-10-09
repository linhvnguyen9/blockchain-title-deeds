package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.composable.Text
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun ConfirmMnemonicScreen(onClickSubmit: () -> Unit) {
    Column(screenModifier) {
        ScreenTitle(title = "Confirm mnemonic", "Reenter your mnemonic")
        Button(onClickSubmit) {
            Text(stringResource(R.string.all_submit))
        }
    }
}