package com.linh.titledeed.presentation.deeds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun TransferDeedOwnershipScreen(
    receiverAddress: String,
    receiverAddressError: String,
    onReceiverAddressChange: (String) -> Unit,
    onClickSubmit: () -> Unit
) {
    val hasError = receiverAddressError.isNotBlank()
    Column(screenModifier) {
        ScreenTitle(stringResource(R.string.all_transfer))
        TextField(
            receiverAddress,
            label = { Text(text = stringResource(R.string.all_receiver_address)) },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onReceiverAddressChange(it) },
            isError = hasError,
        )
        if (hasError) {
            Text(receiverAddressError, color = MaterialTheme.colors.error)
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onClickSubmit() }) {
            Text(stringResource(R.string.all_submit))
        }
    }
}