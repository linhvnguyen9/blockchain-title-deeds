package com.linh.titledeed.presentation.deeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun SellDeedScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    salePriceInWei: String,
    salePriceInWeiError: String,
    onSalePriceChange: (String) -> Unit,
    onClickSubmit: () -> Unit
) {
    val localFocusManager = LocalFocusManager.current

    Column(screenModifier.then(Modifier.verticalScroll(rememberScrollState()))) {
        ScreenTitle(stringResource(R.string.sell_deed_screen_title))
        TextField(
            title,
            label = { Text(text = stringResource(R.string.all_title)) },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onTitleChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            phoneNumber,
            label = { Text(text = stringResource(R.string.sell_phone_number)) },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onPhoneNumberChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            salePriceInWei,
            label = { Text(text = stringResource(R.string.sell_deed_price)) },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onSalePriceChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            )
        )
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
        Button(onClick = { onClickSubmit() }, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_submit))
        }
    }
}