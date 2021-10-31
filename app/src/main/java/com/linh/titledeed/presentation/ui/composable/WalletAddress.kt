package com.linh.titledeed.presentation.ui.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.linh.titledeed.R
import com.linh.titledeed.presentation.ui.theme.ubuntu
import com.linh.titledeed.presentation.utils.copy
import com.linh.titledeed.presentation.utils.getTruncatedAddress

@ExperimentalMaterialApi
@Composable
fun WalletAddress(address: String) {
    val context = LocalContext.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            address.getTruncatedAddress(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = ubuntu,
            fontSize = 18.sp
        )
        IconButton(
            onClick = {
                copy(context, address)
                Toast.makeText(context, "Copied address!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(painterResource(R.drawable.ic_baseline_content_copy_24), null)
        }
    }
}