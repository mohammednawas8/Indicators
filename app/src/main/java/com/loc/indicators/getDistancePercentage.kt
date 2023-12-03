package com.loc.indicators

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

// These calculations will show 7 dots on each half
fun getDistancePercentage(distance: Int, factor: Int): Float {
//    return when (distance) {
//        in 0..3 -> 0.7f
//        4 -> 0.5f
//        5 -> 0.35f
//        6 -> 0.2f
//        7 -> 0.1f
//        else -> 0f
//    }
    return 1f - distance / (factor + 1f)
}

// Visible dots = 7
// distance = 2
// 1 - 7/7 = 0
// 1- 1/8
@Composable
fun Dot(modifier: Modifier = Modifier, size: Dp, color: Color) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
            .animateContentSize()
    )
}