package com.linh.titledeed.presentation.deeds

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SellDeedViewModel @Inject constructor(): ViewModel() {
    private val _priceInWei = MutableStateFlow("")
    val priceInWei: StateFlow<String> get() = _priceInWei

    private val _saleTitle = MutableStateFlow("")
    val saleTitle: StateFlow<String> get() = _saleTitle

    private val _saleDescription = MutableStateFlow("")
    val saleDescription: StateFlow<String> get() = _saleDescription

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

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
        //TODO: Upload JSON metadata
        //TODO: Create sale transaction
    }
}