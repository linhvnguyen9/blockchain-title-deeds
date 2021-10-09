package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.BlockchainTitleDeedsTheme
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun MnemonicScreen(onClickConfirm: () -> Unit) {
    Column(screenModifier) {
        ScreenTitle("Confirm your mnemonic", "Write down these word phrase and store it in a safe place. This is used to recover your wallet")
        Button(onClickConfirm) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MnemonicScreenPreview() {
    BlockchainTitleDeedsTheme(darkTheme = false) {
        MnemonicScreen() {

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun MnemonicScreenDarkPreview() {
    BlockchainTitleDeedsTheme(darkTheme = true) {
        MnemonicScreen() {

        }
    }
}