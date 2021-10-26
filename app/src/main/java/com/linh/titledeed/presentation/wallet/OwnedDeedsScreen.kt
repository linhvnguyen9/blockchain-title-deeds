package com.linh.titledeed.presentation.wallet

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier
import com.linh.titledeed.presentation.ui.theme.superscript
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun OwnedDeedsScreen(deeds: List<Deed>?, isRefreshing: Boolean, onClickDeed: (Deed) -> Unit, onRefresh: () -> Unit) {
    SwipeRefresh(rememberSwipeRefreshState(isRefreshing), onRefresh = onRefresh) {
        Column(Modifier.then(screenModifier)) {
            ScreenTitle("Owned deeds")
            OwnedDeedsList(deeds, onClickDeed)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun OwnedDeedsList(deeds: List<Deed>?, onClickDeed: (Deed) -> Unit) {
    Timber.d("Received deeds $deeds")

    LazyColumn(Modifier.fillMaxSize()) {
        if (deeds == null) {
            items(PLACEHOLDER_COUNT) {
                OwnedDeedItem(null) {}
                Spacer(Modifier.height(12.dp))
            }
        } else {
            itemsIndexed(deeds) { index, item ->
                OwnedDeedItem(item) {
                    onClickDeed(item)
                }
                if (index < deeds.size - 1) {
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OwnedDeedItem(deed: Deed?, onClickDeed: () -> Unit) {
    val placeholderVisible = deed == null
    val placeholderModifier = Modifier.placeholder(placeholderVisible, highlight = PlaceholderHighlight.shimmer())

    Card(
        onClick = {
            onClickDeed()
        },
        Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(deed?.address ?: "",
                Modifier
                    .fillMaxWidth()
                    .then(placeholderModifier), style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .then(placeholderModifier)) {
                Text(
                    buildAnnotatedString {
                        append(deed?.areaInSquareMeters.toString() + " m")
                        withStyle(superscript) {
                            append("2")
                        }
                    },
                    style = MaterialTheme.typography.body1
                )
                Text("-",
                    Modifier
                        .padding(horizontal = 8.dp)
                        .then(placeholderModifier), style = MaterialTheme.typography.body1)
                val purpose = when (deed?.purpose) {
                    LandPurpose.RESIDENTIAL -> stringResource(R.string.land_purpose_residential)
                    LandPurpose.AGRICULTURAL -> stringResource(R.string.land_purpose_agricultural)
                    LandPurpose.NON_AGRICULTURAL -> stringResource(R.string.land_purpose_non_agricultural)
                    else -> ""
                }
                Text(purpose, style = MaterialTheme.typography.body1)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun OwnedDeedsScreenPreview() {
    Column {
        OwnedDeedsScreen(
            listOf(
                Deed(
                    "1",
                    "Test",
                    "Image",
                    "",
                    0.0,
                    0,
                    false,
                    LandPurpose.AGRICULTURAL,
                    1,
                    1
                ), Deed(
                    "1",
                    "Test 2", "Image", "", 0.0, 0, false, LandPurpose.AGRICULTURAL, 1, 1
                )
            ),
            isRefreshing = false,
            onClickDeed = {},
        ) {

        }
    }
}

const val PLACEHOLDER_COUNT = 6
