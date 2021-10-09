package com.linh.titledeed.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.composable.Text

@Composable
fun WelcomeScreen(onClickContinue: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.align(Alignment.Center)
        ) {
            Text(
                "Vietnam Blockchain Title Deeds",
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(32.dp))
            Image(
                painterResource(R.drawable.image_welcome),
                null,
                Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))
            Button(onClickContinue, Modifier.align(Alignment.CenterHorizontally)) {
                Text(stringResource(R.string.all_continue))
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen {

    }
}