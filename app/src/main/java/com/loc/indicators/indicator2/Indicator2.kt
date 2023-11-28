package com.loc.indicators.indicator2

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loc.indicators.Dot

@Composable
fun Indicator2(
    modifier: Modifier = Modifier,
    dots: List<Dot>,
    selectedDotIndex: Int,
    spacing: Dp = 10.dp
) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(spacing),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        dots.forEachIndexed { index, dot ->
//            val size = if (index == selectedDotIndex) DotSize.SELECTED.size else DotSize.NORMAL.size
//            Dot(
//                color = dot.color,
//                size = size
//            )
//        }
//    }
}
