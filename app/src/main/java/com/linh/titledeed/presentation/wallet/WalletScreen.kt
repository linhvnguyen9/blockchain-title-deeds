package com.linh.titledeed.presentation.wallet

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.composable.WalletAddress
import com.linh.titledeed.presentation.ui.theme.chip
import com.linh.titledeed.presentation.ui.theme.screenModifier
import com.linh.titledeed.presentation.ui.theme.ubuntu
import com.linh.titledeed.presentation.utils.copy
import com.linh.titledeed.presentation.utils.getTruncatedAddress

@ExperimentalMaterialApi
@Composable
fun WalletScreen(wallet: Wallet, ethBalance: String, onClickLogout: () -> Unit, onClickViewOwnedDeeds: () -> Unit) {
    Column(
        screenModifier
            .then(Modifier.verticalScroll(rememberScrollState()))
    ) {
        ScreenTitle(title = "Your wallet")

        WalletInfo(ethBalance, wallet)

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = { onClickViewOwnedDeeds() }) {
            Text("Owned deeds")
        }
        TextButton(onClick = { onClickLogout() }) {
            Text(stringResource(R.string.all_logout))
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun WalletInfo(ethBalance: String, wallet: Wallet) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text("Balance", style = MaterialTheme.typography.subtitle1)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(ethBalance, style = MaterialTheme.typography.h3)
                Spacer(Modifier.width(8.dp))
                Icon(painterResource(R.drawable.ic_bnb), null, tint = Color(0xFFf3ba2f))
            }
            Spacer(Modifier.height(16.dp))
            Text(
                stringResource(R.string.all_wallet_address),
                style = MaterialTheme.typography.subtitle1
            )
            WalletAddress(wallet.address)
        }
    }
}