package com.linh.titledeed.presentation.deeds.createdeed

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.presentation.ui.composable.InputCaptionText
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateDeedScreen(
    address: String,
    onAddressChange: (String) -> Unit,
    area: String,
    onAreaChange: (String) -> Unit,
    purpose: LandPurpose,
    onPurposeChange: (LandPurpose) -> Unit,
    isPrivate: Boolean,
    onIsPrivateChange: (Boolean) -> Unit,
    issueDate: Calendar,
    onIssueDateChange: (Calendar) -> Unit,
    photoUri: Uri?,
    onChoosePhoto: (Uri?) -> Unit,
    landNo: String,
    onLandNoChange: (String) -> Unit,
    mapNo: String,
    onMapNoChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit,
    onClickSubmit: () -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        onChoosePhoto(uri)
    }

    val purposeExpanded = rememberSaveable { mutableStateOf(false) }
    val isPrivateExpanded = rememberSaveable { mutableStateOf(false) }

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
        InputCaptionText(Modifier.fillMaxWidth(), R.string.deed_detail_purpose)
        ExposedDropdownMenuBox(modifier = Modifier.fillMaxWidth(), expanded = purposeExpanded.value, onExpandedChange = { purposeExpanded.value = it }) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = purpose.getLandPurposeString(),
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = purposeExpanded.value
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(expanded = purposeExpanded.value, onDismissRequest = { purposeExpanded.value = false }) {
                LandPurpose.values().forEach { landPurpose ->
                    val text = landPurpose.getLandPurposeString()
                    DropdownMenuItem(onClick = {
                        onPurposeChange(landPurpose)
                        purposeExpanded.value = false
                    }) {
                        Text(text)
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        InputCaptionText(Modifier.fillMaxWidth(), R.string.deed_detail_ownership)
        ExposedDropdownMenuBox(modifier = Modifier.fillMaxWidth(), expanded = isPrivateExpanded.value, onExpandedChange = { isPrivateExpanded.value = it }) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = if (isPrivate) stringResource(R.string.deed_detail_private) else stringResource(R.string.deed_detail_shared),
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isPrivateExpanded.value
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(expanded = isPrivateExpanded.value, onDismissRequest = { isPrivateExpanded.value = false }) {
                DropdownMenuItem(onClick = {
                    onIsPrivateChange(true)
                    isPrivateExpanded.value = false
                }) {
                    Text(stringResource(R.string.deed_detail_private))
                }
                DropdownMenuItem(onClick = {
                    onIsPrivateChange(false)
                    isPrivateExpanded.value = false
                }) {
                    Text(stringResource(R.string.deed_detail_shared))
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        InputCaptionText(Modifier.fillMaxWidth(), R.string.deed_detail_issue_date)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val currentYear = issueDate.get(Calendar.YEAR)
            val currentDate = issueDate.get(Calendar.DATE)
            val currentMonth = issueDate.get(Calendar.MONTH)

            Text(formatDate(issueDate))
            TextButton(onClick = {
                val datePickerDialog = DatePickerDialog(context,
                    { view, year, month, dayOfMonth ->
                        val newDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }
                        onIssueDateChange(newDate)
                    }, currentYear, currentMonth, currentDate)
                datePickerDialog.show()
            }) {
                Text(stringResource(R.string.all_submit))
            }
        }
        Spacer(Modifier.height(16.dp))
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
        Spacer(Modifier.height(16.dp))
        InputCaptionText(Modifier.fillMaxWidth(), R.string.create_deed_document_photo)
        if (photoUri != null) {
            Image(
                painter = rememberImagePainter(photoUri, builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_image_24)
                }),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        TextButton(onClick = { launcher.launch("image/*") }) {
            Text(stringResource(R.string.create_deed_pick_photo))
        }
        Spacer(Modifier.height(32.dp))
        Button(onClick = onClickSubmit, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.all_issue_deed_button))
        }
    }
}

@Composable
private fun LandPurpose.getLandPurposeString() = when (this) {
    LandPurpose.RESIDENTIAL -> stringResource(R.string.land_purpose_residential)
    LandPurpose.AGRICULTURAL -> stringResource(R.string.land_purpose_agricultural)
    LandPurpose.NON_AGRICULTURAL -> stringResource(R.string.land_purpose_non_agricultural)
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

private fun formatDate(date: Calendar): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(date.time)
}