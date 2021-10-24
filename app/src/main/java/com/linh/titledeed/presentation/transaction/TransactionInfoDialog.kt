package com.linh.titledeed.presentation.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType

@Composable
fun TransactionInfoDialog(transaction: Transaction?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Column {
        Text(stringResource(R.string.transaction_info_transaction))

        val transactionName = when (transaction?.type) {
            TransactionType.TRANSFER_OWNERSHIP -> stringResource(R.string.all_transfer)
            else -> ""
        }

        Column {
            Text("Transaction name: $transactionName")
            Text("Gas cost : ${transaction?.gasPriceInWei} wei")
        }

        Row(
            Modifier
                .padding(start = 28.dp, end = 28.dp, bottom = 28.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { onConfirm() }) {
                Text("Confirm")
            }
        }
    }
}