package com.linh.titledeed.presentation.deeds.createdeed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun CreateDeedScreen() {
    Column(
        screenModifier
            .then(Modifier.verticalScroll(rememberScrollState()))
    ) {
        ScreenTitle(title = stringResource(R.string.wallet_create_deed))
    }


}