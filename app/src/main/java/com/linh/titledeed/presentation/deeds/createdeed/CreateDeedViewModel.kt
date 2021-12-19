package com.linh.titledeed.presentation.deeds.createdeed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateDeedViewModel @Inject constructor(): ViewModel() {
    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _area = MutableStateFlow("")
    val area: StateFlow<String> get() = _area

    private val _landNo = MutableStateFlow("")
    val landNo: StateFlow<String> get() = _landNo

    private val _mapNo = MutableStateFlow("")
    val mapNo: StateFlow<String> get() = _mapNo

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> get() = _notes

    fun onAddressChange(address: String) {
        _address.value = address
    }

    fun onAreaChange(area: String) {
        _area.value = area.getValidatedNumber()
    }

    fun onLandNoChange(landNo: String) {
        _landNo.value = landNo.replace("[\\.,]".toRegex(), "")
    }

    fun onMapNoChange(mapNo: String) {
        _mapNo.value = mapNo.replace("[\\.,]".toRegex(), "")
    }

    fun onNotesChange(notes: String) {
        _notes.value = notes
    }

    fun onClickSubmit() {
        TODO("Not yet implemented")
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