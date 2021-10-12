package com.linh.titledeed.presentation.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MnemonicItem(word: String, selected: Boolean, onClick: () -> Unit) {
    Box(Modifier.clickable { onClick() }) {
        Surface(shape = RoundedCornerShape(16.dp), elevation = 4.dp) {
            Text(word, Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }
    }
}

@Composable
fun MnemonicList(modifier: Modifier = Modifier, mnemonicWords: List<String>, onSelect: (String) -> Unit) {
    FlowRow(modifier, mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
        mnemonicWords.forEachIndexed { _: Int, word: String ->
            MnemonicItem(word, true) {
                onSelect(word)
            }
        }
    }
}