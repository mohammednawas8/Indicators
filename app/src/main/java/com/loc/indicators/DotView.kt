package com.loc.indicators

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.loc.indicators.indicator1.ANIMATION_DURATION
import com.loc.indicators.indicator2.DotSize

@Composable
fun Dot(modifier: Modifier = Modifier, color: Color, isSelected: Boolean, isSmallDot: Boolean, isExtraSmall: Boolean) {

    val animatedSize by animateDpAsState(
        targetValue = if (isSelected)
            DotSize.SELECTED.size
        else if (isSmallDot) DotSize.SMALL.size
        else if (isExtraSmall) DotSize.EXTRA_SMALL.size
        else DotSize.NORMAL.size,
        label = "size animation",
        animationSpec = tween(ANIMATION_DURATION)
    )
    Box(
        modifier = modifier
            .size(animatedSize)
            .clip(CircleShape)
            .background(color)
    )
}