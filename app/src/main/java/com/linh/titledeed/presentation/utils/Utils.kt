package com.linh.titledeed.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun getErrorStringResource(@StringRes id: Int) = if (id != 0) stringResource(id) else ""