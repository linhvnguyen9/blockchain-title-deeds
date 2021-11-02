package com.linh.titledeed.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.math.BigInteger

@Composable
fun getErrorStringResource(@StringRes id: Int) = if (id != 0) stringResource(id) else ""

fun BigInteger.convertToBalanceString(): String {
    val integerPart = this.divide(ETH_DECIMALS)
    val fractionalPart = this.mod(ETH_DECIMALS).toString(10).replace("0*\$".toRegex(), "")
    return "$integerPart.$fractionalPart"
}

fun String.getTruncatedAddress() : String {
    val addressFirstPart = this.take(8)
    val addressLastPart = this.takeLast(6)
    return "$addressFirstPart...$addressLastPart"
}

fun copy(context: Context, content: String) {
    val clipboard: ClipboardManager? =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("", content)
    clipboard?.setPrimaryClip(clip)
}

private val ETH_DECIMALS = BigInteger("1000000000000000000")