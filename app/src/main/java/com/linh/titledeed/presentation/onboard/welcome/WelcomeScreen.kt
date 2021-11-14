package com.linh.titledeed.presentation.onboard.welcome

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
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
import com.linh.titledeed.R
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity

@ExperimentalAnimationApi
@Composable
fun WelcomeScreen(onClickContinue: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.align(Alignment.Center)
        ) {
            val visible = remember { mutableStateOf(false) }
            val density = LocalDensity.current

            LaunchedEffect(true) {
                visible.value = true
            }

            AnimatedVisibility(
                visible.value,
                enter = slideInVertically(
                    // Slide in from 40 dp from the top.
                    initialOffsetY = { with(density) { -40.dp.roundToPx() } }
                ) + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Image(painterResource(R.drawable.ic_launcher_foreground), null,
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp), contentScale = ContentScale.FillWidth)
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen {

    }
}