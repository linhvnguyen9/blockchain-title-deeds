package com.linh.titledeed.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.linh.titledeed.presentation.ui.composable.Text
import com.linh.titledeed.presentation.ui.theme.screenModifier

@Composable
fun HomeScreen() {
    Column(screenModifier) {
        Text("Home Screen!")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

}