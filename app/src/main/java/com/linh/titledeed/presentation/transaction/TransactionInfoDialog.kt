package com.linh.titledeed.presentation.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.data.entity.TokenOwnerException
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.utils.Resource

@Composable
fun TransactionInfoDialog(transaction: Resource<Transaction>?, response: Resource<String>?, onConfirm: () -> Unit, onDismiss: (popUpTo: Boolean) -> Unit) {
    Card {
        Column(
            Modifier
                .padding(18.dp)
                .height(260.dp)) {
            when {
                response?.isError() == true || transaction?.isError() == true -> {
                    val errorMessage = if (response?.data != null) {
                        response.data
                    } else {
                        when (transaction?.error) {
                            is TokenOwnerException -> "You're no longer the owner of the token"
                            else -> ""
                        }
                    }
                    Text(stringResource(R.string.transaction_info_error), style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(20.dp))
                    Text(errorMessage, style = MaterialTheme.typography.body1)
                    Row(
                        Modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { onDismiss(true) }) {
                            Text(stringResource(R.string.all_ok))
                        }
                    }
                }
                response == null -> {
                    Text(stringResource(R.string.transaction_info_transaction), style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(20.dp))

                    val transactionName = when (transaction?.data?.type) {
                        TransactionType.TRANSFER_OWNERSHIP -> stringResource(R.string.all_transfer)
                        else -> ""
                    }
                    Text(stringResource(R.string.transaction_info_transaction_name), style = MaterialTheme.typography.subtitle2)
                    Text(transactionName, style = MaterialTheme.typography.body1)
                    Spacer(Modifier.height(8.dp))
                    if (transaction?.data?.receiverAddress?.isBlank() == false) {
                        Text(stringResource(R.string.transaction_info_receiver_address), style = MaterialTheme.typography.subtitle2)
                        Text(transaction.data.receiverAddress, style = MaterialTheme.typography.body1)
                        Spacer(Modifier.height(8.dp))
                    }
                    Text(stringResource(R.string.transaction_info_gas_cost), style = MaterialTheme.typography.subtitle2)
                    Text("${transaction?.data?.gasPriceInWei} wei", style = MaterialTheme.typography.body1)
                    Spacer(Modifier.height(20.dp))
                    Row(
                        Modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { onDismiss(false) }) {
                            Text(stringResource(R.string.all_cancel))
                        }
                        Spacer(Modifier.width(8.dp))
                        TextButton(onClick = { onConfirm() }) {
                            Text(stringResource(R.string.all_confirm))
                        }
                    }
                }
                response.isSuccessful() -> {
                    Text(stringResource(R.string.transaction_info_success), style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(20.dp))
                    Row(
                        Modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { onDismiss(true) }) {
                            Text(stringResource(R.string.all_ok))
                        }
                    }
                }
                response.isLoading() -> {
                    Text(stringResource(R.string.transaction_info_loading), style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(20.dp))
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}