package com.linh.titledeed.presentation.onboard.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

@Composable
fun WalletScreen(
    onClickInput: () -> Unit, onClickCreate: () -> Unit
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(R.drawable.bitmap), null)
            Spacer(Modifier.height(32.dp))
            Text(
                stringResource(R.string.wallet_screen_title),
                style = MaterialTheme.typography.h4
            )
            Spacer(Modifier.height(16.dp))
            Text(
                stringResource(R.string.wallet_screen_description),
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.height(32.dp))
            Button(onClickInput, Modifier.width(100.dp)) {
                Text(stringResource(R.string.wallet_input))
            }
            Spacer(Modifier.height(16.dp))
            Button(onClickCreate, Modifier.width(100.dp)) {
                Text(stringResource(R.string.wallet_create))
            }
        }
    }
}

@Composable
@Preview
fun WalletScreenPreview() {
    WalletScreen(onClickInput = { /*TODO*/ }) {

    }
}