package com.linh.titledeed.presentation.deeds

import androidx.compose.material.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.domain.usecase.GetDeedDetailUseCase
import com.linh.titledeed.presentation.NavigationCommand
import com.linh.titledeed.presentation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeedDetailViewModel @Inject constructor(private val getDeedDetailUseCase: GetDeedDetailUseCase, private val navigationManager: NavigationManager): ViewModel() {
    private val _deed = MutableStateFlow(Deed("", "", "", "", 0.0, 0, false, LandPurpose.NON_AGRICULTURAL, 0, 0))
    val deed : StateFlow<Deed> get() = _deed

    fun getDeed(tokenId: String) {
        viewModelScope.launch {
            _deed.value = getDeedDetailUseCase(tokenId)
        }
    }

    fun onClickTransfer() {
        navigationManager.navigate(NavigationDirections.transferOwnership)
    }
}