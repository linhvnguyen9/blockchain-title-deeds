package com.linh.titledeed.presentation.onboard.wallet.createwallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.linh.titledeed.presentation.ui.composable.MnemonicItem
import com.linh.titledeed.presentation.ui.composable.MnemonicList
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun ConfirmMnemonicScreen(
    mnemonicWords: List<String>,
    selectedMnemonicWords: List<String>,
    mnemonicError: String,
    onAddWord: (String) -> Unit,
    onRemoveWord: (String) -> Unit,
    onClickSubmit: () -> Unit
) {
    Column(screenModifier) {
        ScreenTitle(
            title = stringResource(R.string.confirm_mnemonic_screen_title),
            stringResource(R.string.confirm_mnemonic_screen_subtitle)
        )
        MnemonicList(Modifier.fillMaxWidth(), selectedMnemonicWords, onSelect = {
            onRemoveWord(it)
        })
        Text(mnemonicError, color = MaterialTheme.colors.error)
        MnemonicList(Modifier.fillMaxWidth(), mnemonicWords, onSelect = {
            onAddWord(it)
        })

        Spacer(Modifier.height(16.dp))

        Button(onClickSubmit, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Preview
@Composable
fun ConfirmMnemonicScreenPreview() {
    ConfirmMnemonicScreen(
        listOf(
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test"
        ),
        listOf(
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test"
        ),
        "",
        {},
        {}
    ) {

    }
}

@Preview
@Composable
fun MnemonicItemPreview() {
    MnemonicItem("mammal", false) {

    }
}