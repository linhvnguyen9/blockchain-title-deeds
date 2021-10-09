package com.linh.titledeed.presentation.ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitle(title: String, subtitle: String = "") {
    Text(
        title,
        style = MaterialTheme.typography.h4
    )
    if (subtitle.isNotBlank()) {
        Text(
            subtitle,
            style = MaterialTheme.typography.subtitle1
        )
    }
    Spacer(Modifier.height(32.dp))
}