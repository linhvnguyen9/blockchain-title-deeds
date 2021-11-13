package com.linh.titledeed.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.LandPurpose
import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.presentation.ui.composable.ScreenTitle
import com.linh.titledeed.presentation.ui.theme.screenModifier
import com.linh.titledeed.presentation.ui.theme.superscript
import org.bouncycastle.math.raw.Mod
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    sales: List<Sale>,
    isRefreshing: Boolean,
    searchQuery: String,
    isResidentialFilter: Boolean,
    isAgriculturalFilter: Boolean,
    isNonResidentialFilter: Boolean,
    onResidentialFilterChanged: (Boolean) -> Unit,
    onNonResidentialFilterChanged: (Boolean) -> Unit,
    onAgriculturalFilterChanged: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onClickSale: (Sale) -> Unit,
    onRefresh: () -> Unit
) {
    SwipeRefresh(rememberSwipeRefreshState(isRefreshing), onRefresh = onRefresh) {
        Column(screenModifier) {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    ScreenTitle(stringResource(R.string.home_screen_title))
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        searchQuery,
                        onQueryChange,
                        Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(painterResource(R.drawable.ic_baseline_search_24), null)
                        },
                        placeholder = {
                            Text(stringResource(R.string.home_search_hint))
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.horizontalScroll(rememberScrollState())) {
                        SaleFilterItem(
                            text = stringResource(R.string.home_filter_residential),
                            selected = isResidentialFilter,
                            toggle = onResidentialFilterChanged)
                        Spacer(Modifier.width(8.dp))
                        SaleFilterItem(
                            text = stringResource(R.string.home_filter_non_residential),
                            selected = isNonResidentialFilter,
                            toggle = onNonResidentialFilterChanged)
                        Spacer(Modifier.width(8.dp))
                        SaleFilterItem(
                            text = stringResource(R.string.home_filter_agricultural),
                            selected = isAgriculturalFilter,
                            toggle = onAgriculturalFilterChanged)
                        Spacer(Modifier.width(8.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                }
                itemsIndexed(sales) { _: Int, item: Sale ->
                    SaleItem(item) {
                        onClickSale(item)
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun SaleFilterItem(text: String, selected: Boolean, toggle: (Boolean) -> Unit) {
    val borderModifier = if (!selected) Modifier.border(
        1.dp,
        Color.LightGray,
        RoundedCornerShape(8.dp)
    ) else Modifier
    val backgroundModifier = if (!selected) Modifier else Modifier.background(MaterialTheme.colors.primaryVariant, RoundedCornerShape(8.dp))

    Row(
        Modifier
            .toggleable(selected, onValueChange = toggle)
            .then(borderModifier)
            .then(backgroundModifier)
            .padding(8.dp)
            .then(backgroundModifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, color = MaterialTheme.colors.onPrimary)
        if (selected) {
            Spacer(Modifier.width(8.dp))
            Icon(painterResource(R.drawable.ic_baseline_close_24), null, Modifier.size(16.dp))
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SaleItem(sale: Sale?, onClick: () -> Unit) {
    val placeholderVisible = sale == null
    val placeholderModifier =
        Modifier.placeholder(placeholderVisible, highlight = PlaceholderHighlight.shimmer())

    Card(
        onClick = {
            onClick()
        },
        Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                sale?.title ?: "",
                Modifier
                    .fillMaxWidth()
                    .then(placeholderModifier), style = MaterialTheme.typography.h6
            )
            Spacer(Modifier.height(8.dp))
            if (!sale?.deed?.address.isNullOrBlank()) {
                Text(sale?.deed?.address ?: "", style = MaterialTheme.typography.subtitle1)
            }
            Spacer(Modifier.height(8.dp))
            Row {
                val ownership =
                    if (sale?.deed?.isShared == true) stringResource(R.string.deed_detail_shared) else stringResource(R.string.deed_detail_private)
                val purpose = when (sale?.deed?.purpose) {
                    LandPurpose.RESIDENTIAL -> stringResource(R.string.land_purpose_residential)
                    LandPurpose.AGRICULTURAL -> stringResource(R.string.land_purpose_agricultural)
                    LandPurpose.NON_AGRICULTURAL -> stringResource(R.string.land_purpose_non_agricultural)
                    else -> ""
                }

                Text(buildAnnotatedString {
                    append(sale?.deed?.areaInSquareMeters.toString() + " m")
                    withStyle(superscript) {
                        append("2")
                    }
                }, style = MaterialTheme.typography.body1)
                Text("-", Modifier.padding(horizontal = 4.dp))
                Text(ownership, style = MaterialTheme.typography.body1)
                Text("-", Modifier.padding(horizontal = 4.dp))
                Text(purpose, style = MaterialTheme.typography.body1)
            }

            Spacer(Modifier.height(8.dp))
            Row {
                if (sale?.price != null) {
                    Text("${sale.price} wei", style = MaterialTheme.typography.body1, fontStyle = FontStyle.Italic)
                }
            }
            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))
            val description = if (!sale?.description.isNullOrBlank()) {
                sale?.description ?: ""
            } else {
                stringResource(R.string.home_screen_sale_no_description)
            }
            Text(
                description,
                Modifier
                    .fillMaxWidth()
                    .then(placeholderModifier),
                style = MaterialTheme.typography.body1,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        sales = emptyList(),
        isRefreshing = false,
        searchQuery = "",
        isNonResidentialFilter = false,
        isResidentialFilter = true,
        isAgriculturalFilter = true,
        onClickSale = {},
        onQueryChange = {},
        onAgriculturalFilterChanged = {},
        onNonResidentialFilterChanged = {},
        onResidentialFilterChanged = {},
    ) {

    }
}