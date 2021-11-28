package com.linh.titledeed.presentation.deeds.sell

import android.widget.Button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier
import org.w3c.dom.Text

@Composable
fun SellDeedScreen(
    title: String,
    titleError: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    phoneNumber: String,
    phoneNumberError: String,
    onPhoneNumberChange: (String) -> Unit,
    salePriceInWei: String,
    salePriceInWeiError: String,
    onSalePriceChange: (String) -> Unit,
    uploadMetadataResponse: Resource<String>?,
    onClickSubmit: () -> Unit
) {
    val localFocusManager = LocalFocusManager.current

    Box {
        Column(screenModifier.then(Modifier.verticalScroll(rememberScrollState()))) {
            ScreenTitle(stringResource(R.string.sell_deed_screen_title))
            TextField(
                title,
                label = { Text(text = stringResource(R.string.all_title)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onTitleChange(it) },
                singleLine = true,
                isError = titleError.isNotBlank(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                )
            )
            if (titleError.isNotBlank()) {
                Text(titleError, color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.height(16.dp))
            TextField(
                phoneNumber,
                label = { Text(text = stringResource(R.string.sell_phone_number)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onPhoneNumberChange(it) },
                singleLine = true,
                isError = phoneNumberError.isNotBlank(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                )
            )
            if (phoneNumberError.isNotBlank()) {
                Text(phoneNumberError, color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.height(16.dp))
            TextField(
                salePriceInWei,
                label = { Text(text = stringResource(R.string.sell_deed_price)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onSalePriceChange(it) },
                singleLine = true,
                isError = salePriceInWeiError.isNotBlank(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                )
            )
            if (salePriceInWeiError.isNotBlank()) {
                Text(salePriceInWeiError, color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.height(16.dp))
            TextField(
                description,
                label = {
                    Text(text = stringResource(R.string.all_description))
                },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onDescriptionChange(it) }
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { onClickSubmit() },
                Modifier.align(Alignment.CenterHorizontally),
                enabled = uploadMetadataResponse?.isLoading() != true
            ) {
                Text(stringResource(R.string.all_submit))
            }
        }

        if (uploadMetadataResponse?.isLoading() == true) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}