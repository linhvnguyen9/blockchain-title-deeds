package com.linh.titledeed.presentation.deeds.createdeed

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.InputCaptionText
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun CreateDeedScreen(
    address: String,
    onAddressChange: (String) -> Unit,
    area: String,
    onAreaChange: (String) -> Unit,
    landNo: String,
    onLandNoChange: (String) -> Unit,
    mapNo: String,
    onMapNoChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit,
    onClickSubmit: () -> Unit,
) {
    Column(
        screenModifier
            .then(Modifier.verticalScroll(rememberScrollState()))
    ) {
        ScreenTitle(title = stringResource(R.string.wallet_create_deed))

        CreateDeedTextInputField(
            labelResource = R.string.deed_detail_address,
            text = address,
            onTextChange = onAddressChange
        )
        Spacer(Modifier.height(16.dp))
        CreateDeedNumberInputField(
            labelResource = R.string.deed_detail_area,
            value = area,
            onValueChange = onAreaChange
        )
        Spacer(Modifier.height(16.dp))
        //Purpose
        //Ownership
        //Issue date
        CreateDeedNumberInputField(
            labelResource = R.string.deed_detail_land_no,
            value = landNo,
            onValueChange = onLandNoChange
        )
        Spacer(Modifier.height(16.dp))
        CreateDeedNumberInputField(
            labelResource = R.string.deed_detail_map_no,
            value = mapNo,
            onValueChange = onMapNoChange
        )
        Spacer(Modifier.height(16.dp))
        CreateDeedTextInputField(
            labelResource = R.string.deed_detail_notes,
            text = notes,
            onTextChange = onNotesChange
        )
        //Button to select photos
        Spacer(Modifier.height(32.dp))
        Button(onClick = onClickSubmit, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_issue_deed_button))
        }
    }
}

@Composable
private fun CreateDeedTextInputField(
    @StringRes labelResource: Int,
    text: String,
    onTextChange: (String) -> Unit
) {
    InputCaptionText(Modifier.fillMaxWidth(), labelResource)
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange
    )
}

@Composable
private fun CreateDeedNumberInputField(
    @StringRes labelResource: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    InputCaptionText(Modifier.fillMaxWidth(), labelResource)
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}