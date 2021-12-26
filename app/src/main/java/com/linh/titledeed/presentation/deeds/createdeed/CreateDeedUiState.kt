package com.linh.titledeed.presentation.deeds.createdeed

import android.net.Uri
import androidx.annotation.StringRes
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.presentation.utils.NO_ERROR_STRING_RES
import java.util.*

data class CreateDeedUiState(
    val address: String = "",
    @StringRes val addressErrorRes: Int = NO_ERROR_STRING_RES,
    val onAddressChange: (String) -> Unit,

    val area: String = "",
    @StringRes val areaErrorRes: Int = NO_ERROR_STRING_RES,
    val onAreaChange: (String) -> Unit,

    val purpose: LandPurpose = LandPurpose.RESIDENTIAL,
    val onLandPurposeChange: (LandPurpose) -> Unit,

    val landNo: String = "",
    val onLandNoChange: (String) -> Unit,
    @StringRes val landNoErrorRes: Int = NO_ERROR_STRING_RES,

    val mapNo: String = "",
    @StringRes val mapNoErrorRes: Int = NO_ERROR_STRING_RES,
    val onMapNoChange: (String) -> Unit,

    val notes: String = "",
    val onNotesChange: (String) -> Unit,

    val isPrivate: Boolean = true,
    val onIsPrivateChange: (Boolean) -> Unit,

    val issueDate: Calendar = Calendar.getInstance(),
    val onIssueDateChange: (Calendar) -> Unit,

    val receiverAddress: String = "",
    val onReceiveAddressChange: (String) -> Unit,
    @StringRes val receiverAddressErrorRes: Int = NO_ERROR_STRING_RES,

    val photoUri: Uri? = null,
    val onPickPhoto: (Uri?) -> Unit,
    @StringRes val photoUriErrorRes: Int = NO_ERROR_STRING_RES,

    val onClickSubmit: () -> Unit
)