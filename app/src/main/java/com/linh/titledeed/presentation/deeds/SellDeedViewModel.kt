package com.linh.titledeed.presentation.deeds

import androidx.lifecycle.ViewModel
import com.linh.titledeed.NavigationDirection
import com.linh.titledeed.R
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SellDeedViewModel @Inject constructor(private val navigationManager: NavigationManager): ViewModel() {
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

        //TODO: Upload JSON metadata
        //TODO: Create sale transaction
    }
}