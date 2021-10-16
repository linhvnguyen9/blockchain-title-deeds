package com.linh.titledeed.presentation.wallet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Wallet

@Composable
fun WalletScreen(wallet: Wallet) {
//    val ethBalance = wallet.ethBalance.collectAsState()
//    val wallet = viewModel.wallet.collectAsState(Wallet("", "", ""))
//    val ownedTokens = viewModel.ownedTokens.collectAsState(emptyList())

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
//        Text("ETH balance ${ethBalance.value}", color = MaterialTheme.colors.onSurface)
        WalletAddress(wallet.address)
        Text("My NFTs", Modifier.clickable {

        })
    }
}

@Composable
private fun WalletAddress(address: String) {
    val context = LocalContext.current

    ConstraintLayout(Modifier.fillMaxWidth()) {
        val (addressText, copyButton) = createRefs()

        Text(
            "Wallet address: $address",
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(addressText) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(copyButton.start)
                width = Dimension.fillToConstraints
            }
        )
        IconButton(
            onClick = {
                copy(context, address)
                Toast.makeText(context, "Copied address!", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.constrainAs(copyButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        ) {
            Icon(painterResource(R.drawable.ic_baseline_content_copy_24), null)
        }
    }
}

private fun copy(context: Context, content: String) {
    val clipboard: ClipboardManager? =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("", content)
    clipboard?.setPrimaryClip(clip)
}