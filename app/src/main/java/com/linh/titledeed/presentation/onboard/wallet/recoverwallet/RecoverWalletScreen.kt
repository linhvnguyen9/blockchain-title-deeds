package com.linh.titledeed.presentation.onboard.wallet.recoverwallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.PasswordTextField
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.BlockchainTitleDeedsTheme
import com.linh.titledeed.presentation.ui.theme.screenPadding

@Composable
fun InputWalletScreen(
    isRecoverFromMnemonic: Boolean,
    onIsRecoverFromMnemonicChange: (Boolean) -> Unit,
    password: String,
    passwordError: String,
    onPasswordChange: (String) -> Unit,
    mnemonic: String,
    mnemonicError: String,
    onMnemonicChange: (String) -> Unit,
    privateKey: String,
    privateKeyError: String,
    onPrivateKeyChange: (String) -> Unit,
    onClickSubmit: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(screenPadding)
    ) {
        ScreenTitle(
            title = stringResource(R.string.recover_wallet_screen_title),
            subtitle = stringResource(R.string.recover_wallet_screen_subtitle)
        )

        Column(
            Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            PickMnemonic(
                isSelected = isRecoverFromMnemonic,
                onChange = onIsRecoverFromMnemonicChange,
                label = stringResource(R.string.wallet_mnemonic)
            )
            Spacer(Modifier.height(8.dp))
            PickMnemonic(
                isSelected = !isRecoverFromMnemonic,
                onChange = { onIsRecoverFromMnemonicChange(!it) },
                label = stringResource(R.string.wallet_private_key)
            )
        }

        Spacer(Modifier.height(20.dp))

        if (isRecoverFromMnemonic) {
            Text(stringResource(R.string.wallet_mnemonic))
            TextField(value = mnemonic, onValueChange = onMnemonicChange, Modifier.fillMaxWidth())
            if (mnemonicError.isNotBlank()) {
                Text(mnemonicError, color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.mnemonic_description))

            Spacer(Modifier.height(16.dp))

            Text(stringResource(R.string.wallet_password))
            PasswordTextField(
                value = password,
                onValueChange = onPasswordChange,
                Modifier.fillMaxWidth(),
                errorText = passwordError
            )
        } else {
            Text(stringResource(R.string.wallet_private_key))
            TextField(
                value = privateKey,
                onValueChange = onPrivateKeyChange,
                Modifier.fillMaxWidth()
            )
            if (privateKeyError.isNotBlank()) {
                Text(privateKeyError, color = MaterialTheme.colors.error)
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(onClickSubmit, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_submit))
        }
    }
}

@Composable
fun PickMnemonic(isSelected: Boolean, onChange: (Boolean) -> Unit, label: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            isSelected,
            onClick = { onChange(true) },
            colors = RadioButtonDefaults.colors()
        )
        Spacer(Modifier.width(16.dp))
        Text(label)
    }
}

@Preview
@Composable
fun InputWalletScreenPreview() {
    BlockchainTitleDeedsTheme {
        InputWalletScreen(false, {}, "", "", {}, "", "", {}, "", "", {}) {

        }
    }
}