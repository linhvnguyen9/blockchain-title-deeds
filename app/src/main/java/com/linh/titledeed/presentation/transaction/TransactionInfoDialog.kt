package com.linh.titledeed.presentation.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType

@Composable
fun TransactionInfoDialog(transaction: Transaction?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Card {
        Column(Modifier.padding(18.dp).height(260.dp)) {
            Text(stringResource(R.string.transaction_info_transaction), style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(20.dp))
            val transactionName = when (transaction?.type) {
                TransactionType.TRANSFER_OWNERSHIP -> stringResource(R.string.all_transfer)
                else -> ""
            }
            Text(stringResource(R.string.transaction_info_transaction_name), style = MaterialTheme.typography.subtitle2)
            Text(transactionName, style = MaterialTheme.typography.body1)
            Spacer(Modifier.height(8.dp))
            if (transaction?.receiverAddress?.isBlank() == false) {
                Text(stringResource(R.string.transaction_info_receiver_address), style = MaterialTheme.typography.subtitle2)
                Text(transaction.receiverAddress, style = MaterialTheme.typography.body1)
                Spacer(Modifier.height(8.dp))
            }
            Text(stringResource(R.string.transaction_info_gas_cost), style = MaterialTheme.typography.subtitle2)
            Text("${transaction?.gasPriceInWei} wei", style = MaterialTheme.typography.body1)
            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.all_cancel))
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = { onConfirm() }) {
                    Text(stringResource(R.string.all_confirm))
                }
            }
        }
    }
}