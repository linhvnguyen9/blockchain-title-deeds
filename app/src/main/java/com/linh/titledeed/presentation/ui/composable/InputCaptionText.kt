package com.linh.titledeed.presentation.ui.composable

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun InputCaptionText(modifier: Modifier = Modifier, @StringRes labelRes: Int) {
    InputCaptionText(stringResource(labelRes))
}

@Composable
fun InputCaptionText(label: String) {
    Text(
        label,
        style = MaterialTheme.typography.caption
    )
}