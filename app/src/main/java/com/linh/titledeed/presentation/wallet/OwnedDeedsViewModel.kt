package com.linh.titledeed.presentation.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.Wallet
import com.linh.titledeed.domain.usecase.GetAllOwnedDeedsUseCase
import com.linh.titledeed.domain.usecase.GetWalletInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OwnedDeedsViewModel @Inject constructor(private val getWalletInfoUseCase: GetWalletInfoUseCase, private val getAllOwnedDeedsUseCase: GetAllOwnedDeedsUseCase): ViewModel() {
    private val _wallet = MutableStateFlow(Wallet("", "", "", ""))
    val wallet: StateFlow<Wallet> get() = _wallet

    private val _deeds = MutableStateFlow(emptyList<Deed>())
    val deeds : StateFlow<List<Deed>> get() = _deeds

    init {
        viewModelScope.launch {
            val wallet = getWalletInfoUseCase()
            _deeds.value = getAllOwnedDeedsUseCase(wallet.address)

            Timber.d("Received deeds ${deeds.value}")
        }
    }
}