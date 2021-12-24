package com.linh.titledeed.presentation.ui.composable

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.linh.titledeed.presentation.utils.NO_ERROR_STRING_RES
import com.linh.titledeed.presentation.utils.getErrorStringResource

@Composable
fun ErrorText(modifier: Modifier = Modifier, @StringRes errorRes: Int) {
    if (errorRes != NO_ERROR_STRING_RES) {
        Text(text = getErrorStringResource(errorRes), color = MaterialTheme.colors.error)
    }
}