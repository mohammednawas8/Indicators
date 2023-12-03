package com.loc.indicators

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
fun getDistancePercentage(distance: Int): Float {
    return when (distance) {
        in 1..3 -> 0.7f
        4 -> 0.5f
        5 -> 0.35f
        6 -> 0.2f
        7 -> 0.1f
        else -> 0f
    }
}
@Composable
fun Dot(modifier: Modifier = Modifier, size: Dp, color: Color) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}