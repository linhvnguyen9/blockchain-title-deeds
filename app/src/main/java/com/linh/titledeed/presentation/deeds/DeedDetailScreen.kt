package com.linh.titledeed.presentation.deeds

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.linh.titledeed.R
import com.linh.titledeed.data.utils.DateFormatUtil
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier
import com.linh.titledeed.presentation.ui.theme.superscript
import java.util.*
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@Composable
fun DeedDetailScreen(deed: Deed, onClickTransferOwnership: () -> Unit) {
    val isPreview = remember { mutableStateOf(false) }
    Column(screenModifier.verticalScroll(rememberScrollState())) {
        ScreenTitle(stringResource(R.string.all_deed))
        Text(stringResource(R.string.deed_detail_land_no, deed.landNo))
        Text(stringResource(R.string.deed_detail_map_no, deed.mapNo))
        Text(stringResource(R.string.deed_detail_address, deed.address))
        Text(buildAnnotatedString {
            append(deed.areaInSquareMeters.toString() + " m")
            withStyle(superscript) {
                append("2")
            }
        })
        Text(stringResource(R.string.deed_detail_issue_date, DateFormatUtil.formatDate(Calendar.getInstance().apply { timeInMillis = deed.issueDate })))
        val ownership =
            if (deed.isShared) stringResource(R.string.deed_detail_shared) else stringResource(R.string.deed_detail_private)
        Text(
            stringResource(
                R.string.deed_detail_ownership,
                ownership
            )
        )
        val purpose = when (deed.purpose) {
            LandPurpose.RESIDENTIAL -> stringResource(R.string.land_purpose_residential)
            LandPurpose.AGRICULTURAL -> stringResource(R.string.land_purpose_agricultural)
            LandPurpose.NON_AGRICULTURAL -> stringResource(R.string.land_purpose_non_agricultural)
        }
        Text(
            stringResource(
                R.string.deed_detail_purpose,
                purpose
            )
        )
        Text(stringResource(R.string.deed_detail_notes))
        Text(deed.note)
        Spacer(Modifier.height(4.dp))
        Divider()
        Spacer(Modifier.height(16.dp))
        Image(
            painter = rememberImagePainter(deed.imageUri, builder = {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_image_24)
            }),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { isPreview.value = true }
        )
        Spacer(Modifier.height(32.dp))
        TextButton(onClick = { onClickTransferOwnership() }) {
            Text(stringResource(R.string.all_transfer))
        }
    }
    if (isPreview.value) {
        DeedImagePreviewDialog(deed.imageUri) {
            isPreview.value = false
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun DeedImagePreviewDialog(
    imageUri: String,
    onDismiss: () -> Unit
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        val scale =
            remember { mutableStateOf(1f) }
        val panX =
            remember { mutableStateOf(1f) }
        val panY =
            remember { mutableStateOf(1f) }

        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Image(painter = rememberImagePainter(imageUri, builder = {
                crossfade(true)
                val placeholder =
                    placeholder(R.drawable.ic_baseline_image_24)
            }), contentDescription = null, modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        panX.value.roundToInt(),
                        panY.value.roundToInt()
                    )
                }
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value,
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        if (scale.value in 1f..3f) {
                            //Only allow panning when zoomed in
                            panX.value += pan.x
                            panY.value += pan.y
                        }
                        scale.value = when {
                            scale.value < 1f -> 1f
                            scale.value > 3f -> 3f
                            else -> scale.value * zoom
                        }
                    }
                })
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun DeedDetailScreenPreview() {
    DeedDetailScreen(
        Deed(
            "1",
            "Test 2",
            "Image",
            "",
            0.0,
            0,
            false,
            LandPurpose.AGRICULTURAL,
            1,
            1
        )
    ) {

    }
}