package com.linh.titledeed.presentation.deeds.createdeed

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.usecase.UploadDeedMetadataToIpfsUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import com.linh.titledeed.presentation.utils.NO_ERROR_STRING_RES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateDeedViewModel @Inject constructor(
    private val uploadDeedMetadataToIpfsUseCase: UploadDeedMetadataToIpfsUseCase,
    private val navigationManager: NavigationManager
): ViewModel() {
    private val _uiState: MutableStateFlow<CreateDeedUiState>
    val uiState: StateFlow<CreateDeedUiState> get() = _uiState

    init {
        val defaultUiState = initDefaultUiState()
        _uiState = MutableStateFlow(defaultUiState)
    }

    private fun initDefaultUiState(): CreateDeedUiState {
        return CreateDeedUiState(
            onAddressChange = ::onAddressChange,
            onAreaChange = ::onAreaChange,
            onLandPurposeChange = ::onLandPurposeChange,
            onIsPrivateChange = ::onIsPrivateChange,
            onIssueDateChange = ::onIssueDateChange,
            onLandNoChange = ::onLandNoChange,
            onMapNoChange = ::onMapNoChange,
            onNotesChange = ::onNotesChange,
            onReceiveAddressChange = ::onReceiverAddressChange,
            onPickPhoto = ::onPickPhoto,
            onClickSubmit = ::onClickSubmit,
        )
    }

    private fun onAddressChange(address: String) {
        _uiState.value = uiState.value.copy(address = address)
    }

    private fun onAreaChange(area: String) {
        _uiState.value = uiState.value.copy(area = area.getValidatedNumber())
    }

    private fun onLandPurposeChange(purpose: LandPurpose) {
        _uiState.value = uiState.value.copy(purpose = purpose)
    }

    private fun onIsPrivateChange(isPrivate: Boolean) {
        _uiState.value = uiState.value.copy(isPrivate = isPrivate)
    }

    private fun onIssueDateChange(date: Calendar) {
        _uiState.value = uiState.value.copy(issueDate = date)
    }

    private fun onLandNoChange(landNo: String) {
        _uiState.value = uiState.value.copy(landNo = landNo.replace("[\\.,]".toRegex(), ""))
    }

    private fun onMapNoChange(mapNo: String) {
        _uiState.value = uiState.value.copy(mapNo = mapNo.replace("[\\.,]".toRegex(), ""))
    }

    private fun onNotesChange(notes: String) {
        _uiState.value = uiState.value.copy(notes = notes)
    }

    private fun onReceiverAddressChange(receiverAddress: String) {
        _uiState.value = uiState.value.copy(receiverAddress = receiverAddress)
    }

    private fun onPickPhoto(uri: Uri?) {
        _uiState.value = uiState.value.copy(photoUri = uri)
    }

    private fun onClickSubmit() {
        viewModelScope.launch {
            val currentUiState = uiState.value
            with(currentUiState) {
                var hasError = false
                var newUiState = currentUiState

                if (address.isBlank()) {
                    hasError = true
                    newUiState = newUiState.copy(addressErrorRes = R.string.error_create_deed_address_blank)
                } else {
                    newUiState = newUiState.copy(addressErrorRes = NO_ERROR_STRING_RES)
                }

                if (area.isBlank()) {
                    hasError = true
                    newUiState = newUiState.copy(areaErrorRes = R.string.error_create_deed_area_blank)
                } else {
                    newUiState = newUiState.copy(areaErrorRes = NO_ERROR_STRING_RES)
                }

                if (landNo.isBlank()) {
                    hasError = true
                    newUiState = newUiState.copy(landNoErrorRes = R.string.error_create_deed_land_no_blank)
                } else {
                    newUiState = newUiState.copy(landNoErrorRes = NO_ERROR_STRING_RES)
                }

                if (mapNo.isBlank()) {
                    hasError = true
                    newUiState = newUiState.copy(mapNoErrorRes = R.string.error_create_deed_map_no_blank)
                } else {
                    newUiState = newUiState.copy(mapNoErrorRes = NO_ERROR_STRING_RES)
                }

                if (photoUri == null) {
                    hasError = true
                    newUiState = newUiState.copy(photoUriErrorRes = R.string.error_create_deed_no_photo_error)
                } else {
                    newUiState = newUiState.copy(photoUriErrorRes = NO_ERROR_STRING_RES)
                }

                if (receiverAddress.isBlank()) {
                    hasError = true
                    newUiState = newUiState.copy(receiverAddressErrorRes = R.string.error_create_deed_receiver_address_blank)
                } else {
                    newUiState = newUiState.copy(receiverAddressErrorRes = NO_ERROR_STRING_RES)
                }

                _uiState.value = newUiState

                if (!hasError) {
                    val uploadResponse = uploadDeedMetadataToIpfsUseCase(Deed(
                        "",
                        address,
                        photoUri.toString(),
                        notes,
                        area.toDouble(),
                        issueDate.timeInMillis,
                        !isPrivate,
                        purpose,
                        mapNo.toInt(),
                        landNo.toInt()
                    ))

                    val navDirection = NavigationDirections.TransactionInfoNavigation.transactionInfo(
                        TransactionType.CREATE_DEED,
                        receiverAddress = receiverAddress,
                        uri = uploadResponse.data ?: "",
                        popInclusive = false,
                        navigateBackDestination = NavigationDirections.wallet.destination
                    )
                    navigationManager.navigate(
                        NavigationCommand(
                            navDirection,
                            NavigationDirections.wallet,
                            true
                        )
                    )
                }
            }
        }
    }

    private fun String.getValidatedNumber(): String {
        val filteredChars = filterIndexed { index, c ->
            c in "0123456789" || (c == '.' && indexOf('.') == index)
        }
        return if(filteredChars.contains('.')) {
            val beforeDecimal = filteredChars.substringBefore('.')
            val afterDecimal = filteredChars.substringAfter('.')
            beforeDecimal.take(10) + "." + afterDecimal.take(2)
        } else {
            filteredChars.take(10)
        }
    }
}