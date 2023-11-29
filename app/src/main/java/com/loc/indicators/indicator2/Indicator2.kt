package com.loc.indicators.indicator2

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.loc.indicators.indicator.Dot
import com.loc.indicators.indicator.getDistancePercentage
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun Indicator2(
    modifier: Modifier = Modifier,
    dots:List<Dot>,
    spacing: Dp = 5.dp,
    selectedDotIndex: Int,
    selectedDotSize: Dp = 15.dp,
    unselectedDotSize: Dp = 15.dp,
) {
    Layout(
        modifier = modifier.clipToBounds(),
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val height = selectedDotSize.roundToPx()
            layout(
                width = constraints.maxWidth,
                height = height
            ) {
                // Right group / Center group / Left group
                val maxItemWidth = placeables.maxOfOrNull { it.width } ?: 0
                val originPosition =
                    constraints.maxWidth.div(2f) - maxItemWidth.div(2)
                placeables.forEachIndexed { index, placeable ->
                    val distance = index - selectedDotIndex
                    val yPosition = height / 2 - placeable.height / 2
                    when {
                        // Place on the left
                        distance < 0 -> {
                            // - placeable.width / 2
                            val xPosition =
                                distance.times(spacing.roundToPx()) + distance.times(placeable.width)
                            placeable.place(
                                constraints.maxWidth / 2 + xPosition,
                                yPosition
                            )
                        }
                        // Place in the center
                        distance == 0 -> {
                            placeable.place(originPosition.roundToInt(), yPosition)
                        }
                        // Place on the right
                        else -> {
                            val xPosition =
                                distance.times(spacing.roundToPx()) + distance.times(placeable.width)
                            placeable.place(constraints.maxWidth / 2 + xPosition, yPosition)
                        }
                    }
                }
            }
        },
        content = {
            dots.forEachIndexed { index, dot ->
//                val size by animateDpAsState(targetValue = if (selectedDotIndex == index) 15.dp else 10.dp)
                val distance = abs(index - selectedDotIndex)
                val percentage = getDistancePercentage(distance)
                Dot(size = if (selectedDotIndex == index) 15.dp else unselectedDotSize * percentage, color = dot.color)
            }
        }
    )
}