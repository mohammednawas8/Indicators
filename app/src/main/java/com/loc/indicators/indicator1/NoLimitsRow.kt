package com.loc.indicators.indicator1

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import com.loc.indicators.indicator2.DotSize

// This row is a custom row that has no limit on it's width which suits the indicator row.
@Composable
fun NoLimitsRow(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }
            val rowHeight = placeables.maxByOrNull { it.height }?.height ?: 0
            layout(
                width = constraints.maxWidth,
                height = DotSize.SELECTED.size.roundToPx()
            ) {
                var xPosition = 0
                placeables.forEach { placeable ->
                    val yPosition = (rowHeight - placeable.height) / 2
                    placeable.place(
                        x = xPosition,
                        y = yPosition
                    )
                    xPosition += placeable.width + horizontalSpacing.toPx().toInt()
                }
            }
        },
        content = content
    )
}