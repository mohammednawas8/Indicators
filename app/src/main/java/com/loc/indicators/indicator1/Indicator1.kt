package com.loc.indicators.indicator1

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.loc.indicators.Dot
import com.loc.indicators.indicator2.DotSize
import kotlin.math.abs

const val ANIMATION_DURATION = 150

@Composable
fun Indicator1(
    modifier: Modifier = Modifier,
    dots: List<Dot>,
    horizontalSpacing: Dp = 30.dp,
    visibleDots: Int,
    selectedItemIndex: Int,
    showSmallIconsAfter: Int
) {
    val dotSize = DotSize.NORMAL.size
    val dotSizePx = with(LocalDensity.current) {
        dotSize.toPx()
    }
    val horizontalSpacingPx = with(LocalDensity.current) {
        horizontalSpacing.toPx()
    }
    var currentX by rememberSaveable { mutableStateOf(0) }
    val animatedX by animateIntAsState(
        targetValue = currentX,
        label = "X offset animation",
        animationSpec = tween(ANIMATION_DURATION)
    )
    val visibleItems by remember(selectedItemIndex, visibleDots, dots) {
        mutableStateOf(getVisibleItems(dots, selectedItemIndex, visibleDots))
    }
    NoLimitsRow(
        modifier = Modifier
            .then(modifier)
            .offset {
                IntOffset(animatedX, 0)
            }
            .drawBehind {
                currentX =
                    (center.x - dotSize.value).toInt() - (selectedItemIndex * (dotSizePx + horizontalSpacingPx).toInt())
            }.background(Color.Red),
        horizontalSpacing = horizontalSpacing
    ) {
        dots.forEachIndexed { index, dot ->
            val colorAnimation by animateColorAsState(
                targetValue = if (visibleItems[index]) dot.color else Color.Transparent,
                label = "Color animation",
                animationSpec = tween(ANIMATION_DURATION)
            )
            val distance = abs(selectedItemIndex - index)
            val isSmallDot = false

            Dot(
                color = colorAnimation,
                isSelected = distance == 0,
                isSmallDot = distance > showSmallIconsAfter,
                isExtraSmall = false
            )
        }
    }
}

//[true,true,true,ture,true,true,false,false,false,false]

private fun getSmallDotPosition(
    index: Int,
    flags: List<Boolean>,
): SmallDotLocation {
    return if (flags.size == 1 || flags.isEmpty()) {
        SmallDotLocation.UNKNOWN
    } else if (index == 0 && flags[index]) {
        SmallDotLocation.LEFT
    } else if (index == flags.size - 1 && flags[index]) {
        SmallDotLocation.RIGHT
    } else if (flags[index] && !flags[index - 1]) {
        SmallDotLocation.RIGHT
    } else if (flags[index] && !flags[index + 1]) {
        SmallDotLocation.LEFT
    } else {
        SmallDotLocation.UNKNOWN
    }
}

//@Composable
//private fun getDotModifier(index: Int, selectedIndex: Int, scaleFactor: Float,dot: Dot): Modifier {
//    return if (index == selectedIndex){
//        Modifier.animateScale(scaleFactor)
//    }else{
//
//    }
//}

private fun getVisibleItems(list: List<Dot>, selectedIndex: Int, visibleItems: Int): List<Boolean> {
    val startIndex = maxOf(0, minOf(selectedIndex - visibleItems / 2, list.size - visibleItems))
    val endIndex = minOf(list.size - 1, startIndex + visibleItems - 1)
    return List(list.size) { index ->
        index in startIndex..endIndex
    }
}
