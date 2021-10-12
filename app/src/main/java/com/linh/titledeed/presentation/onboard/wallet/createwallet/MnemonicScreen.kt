package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.MnemonicList
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.BlockchainTitleDeedsTheme
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun MnemonicScreen(mnemonicWords: List<String>, onClickConfirm: () -> Unit) {
    Column(screenModifier) {
        ScreenTitle("Secret Recovery Phrase", "Your Secret Recovery Phrase makes it easy to back up and restore your account.")
        MnemonicList(Modifier.fillMaxWidth(), mnemonicWords, onSelect = {

        })
        Spacer(Modifier.height(16.dp))
        Button(onClickConfirm, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MnemonicScreenPreview() {
    BlockchainTitleDeedsTheme(darkTheme = false) {
        MnemonicScreen(emptyList()) {

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun MnemonicScreenDarkPreview() {
    BlockchainTitleDeedsTheme(darkTheme = true) {
        MnemonicScreen(emptyList()) {

        }
    }
}