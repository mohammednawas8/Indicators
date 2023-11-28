package com.loc.indicators

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.animateScale(scaleFactor: Float): Modifier = composed {
    var triggerAnimation by remember {
        mutableStateOf(false)
    }
    val scaleAnimation by animateFloatAsState(
        targetValue = if (triggerAnimation) scaleFactor else 1f,
        label = "",
    )
    LaunchedEffect(key1 = true) {
        triggerAnimation = true
    }
    graphicsLayer {
        scaleX = scaleAnimation; scaleY = scaleAnimation
    }
}
