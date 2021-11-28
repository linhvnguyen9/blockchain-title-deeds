package com.linh.titledeed.presentation.deeds.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import com.linh.titledeed.domain.usecase.UploadSaleMetadataUseCase
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellDeedViewModel @Inject constructor(
    private val uploadSaleMetadataUseCase: UploadSaleMetadataUseCase,
    private val getWalletInfoUseCase: GetWalletInfoUseCase,
    private val navigationManager: NavigationManager
): ViewModel() {
    private val _priceInWei = MutableStateFlow("")
    val priceInWei: StateFlow<String> get() = _priceInWei
    private val _priceInWeiError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val priceInWeiError: StateFlow<Int> get() = _priceInWeiError

    private val _saleTitle = MutableStateFlow("")
    val saleTitle: StateFlow<String> get() = _saleTitle
    private val _saleTitleError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val saleTitleError: StateFlow<Int> get() = _saleTitleError

    private val _saleDescription = MutableStateFlow("")
    val saleDescription: StateFlow<String> get() = _saleDescription

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber
    private val _phoneNumberError = MutableStateFlow<@androidx.annotation.StringRes Int>(0)
    val phoneNumberError: StateFlow<Int> get() = _phoneNumberError

    private val _uploadMetadataResponse = MutableStateFlow<Resource<String>?>(null)
    val uploadMetadataResponse: StateFlow<Resource<String>?> get() = _uploadMetadataResponse

    private lateinit var tokenId: String

    fun setTokenId(tokenId: String) {
        this.tokenId = tokenId
    }

    fun setPriceInWei(price: String) {
        _priceInWei.value = price
    }

    fun setSaleTitle(title: String) {
        _saleTitle.value = title
    }

    fun setSaleDescription(description: String) {
        _saleDescription.value = description
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun onClickSubmit() {
        var hasError = false
        if (saleTitle.value.isBlank()) {
            _saleTitleError.value = R.string.error_sell_property_title_blank
            hasError = true
        } else {
            _saleTitleError.value = 0
        }
        if (phoneNumber.value.isBlank()) {
            _phoneNumberError.value = R.string.error_sell_property_phone_blank
            hasError = true
        } else {
            _phoneNumberError.value = 0
        }
        if (priceInWei.value.isBlank()) {
            _priceInWeiError.value = R.string.error_sell_property_price_blank
            hasError = true
        } else {
            _priceInWeiError.value = 0
        }

        if (hasError) {
            return
        }

        viewModelScope.launch {
            val title = saleTitle.value
            val description = saleDescription.value
            val phoneNumber = phoneNumber.value
            val priceInWei = priceInWei.value

            val sale = Sale(tokenId, title, description, phoneNumber, emptyList()) //TODO: Add support for images
            _uploadMetadataResponse.value = Resource.loading()
            val uploadResponse = uploadSaleMetadataUseCase(sale)
            _uploadMetadataResponse.value = uploadResponse

            val navDirection = NavigationDirections.TransactionInfoNavigation.transactionInfo(
                TransactionType.CREATE_SALE,
                "",
                tokenId,
                priceInWei,
                uploadResponse.data ?: "",
                NavigationDirections.DeedDetailNavigation.route,
                false
            )
            navigationManager.navigate(
                NavigationCommand(
                    navDirection,
                    NavigationDirections.DeedDetailNavigation.detail(tokenId),
                    true
                )
            )
        }
    }
}