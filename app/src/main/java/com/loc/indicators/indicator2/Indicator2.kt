package com.loc.indicators.indicator2

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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
    dots: List<Dot>,
    spacing: Dp = 5.dp,
    selectedDotIndex: Int,
    selectedDotSize: Dp = 15.dp,
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
                var rightItemsWidth = 0

                //Left dots
                var leftItemsWidth = 0
                placeables.subList(0,selectedDotIndex).asReversed().forEachIndexed { distance, placeable ->
                    val yPosition = height / 2 - placeable.height / 2
                    val xPosition = originPosition.roundToInt() - spacing.roundToPx() - placeable.width - leftItemsWidth
                    placeable.place(x = xPosition, y = yPosition)
                    leftItemsWidth += placeable.width + spacing.roundToPx()
                }

                //Middle dot
                placeables[selectedDotIndex].place(originPosition.roundToInt(),0)

                //Right dots
                placeables.subList(selectedDotIndex + 1,placeables.size).forEachIndexed { distance, placeable ->
                    val yPosition = height / 2 - placeable.height / 2
                    val xPosition = originPosition + placeable.width + rightItemsWidth + spacing.roundToPx()
                            placeable.place(x = xPosition.roundToInt(), y = yPosition)
                            rightItemsWidth += placeable.width + spacing.roundToPx()
                }
            }
        },
        content = {
            dots.forEachIndexed { index, dot ->
                val distance = abs(index - selectedDotIndex)
                val percentage = getDistancePercentage(distance)
                Dot(
                    size = if (index == selectedDotIndex) 15.dp else percentage * selectedDotSize,
                    color = dot.color
                )
            }
        }
    )
}


