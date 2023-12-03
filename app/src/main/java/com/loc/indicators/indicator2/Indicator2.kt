package com.loc.indicators.indicator2

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loc.indicators.Dot
import com.loc.indicators.getDistancePercentage
import kotlin.math.abs
import kotlin.math.roundToInt

class IndicatorState(
    initialSelectedDotIndex: Int,
    val totalDots: List<Dot>,
    private val visibleDotsCount: Int = 14,
) {
    var currentDot by mutableIntStateOf(initialSelectedDotIndex)

    var visibleDots = mutableStateListOf(totalDots.first())
    var currentIndexFromVisibleDots by mutableIntStateOf(0)


    @Composable
    fun VisibleDotsObserver() {
        LaunchedEffect(key1 = currentDot) {
            val (dots, index) = getVisibleItems(totalDots, currentDot, visibleDotsCount)
            visibleDots.clear()
            visibleDots.addAll(dots)
            currentIndexFromVisibleDots = index
        }
    }

    private fun getVisibleItems(
        inputList: List<Dot>,
        selectedIndex: Int,
        visibleItems: Int
    ): Pair<List<Dot>, Int> {
        val halfVisibleItems = visibleItems / 2
        val adjustedStartIndex =
            (selectedIndex - halfVisibleItems).coerceIn(0, inputList.size - visibleItems)
        val adjustedEndIndex = adjustedStartIndex + visibleItems

        val newList = inputList.subList(adjustedStartIndex, adjustedEndIndex)
        val newIndex = selectedIndex - adjustedStartIndex

        return newList to newIndex
    }

    fun moveNext() {
        currentDot = (currentDot + 1).coerceIn(1, totalDots.size - 1)
        currentIndexFromVisibleDots =
            (currentIndexFromVisibleDots + 1).coerceIn(1, visibleDotsCount - 1)
    }

    fun movePrevious() {
        currentDot = (currentDot - 1).coerceIn(0, totalDots.size - 2)
        currentIndexFromVisibleDots =
            (currentIndexFromVisibleDots - 1).coerceIn(0, visibleDotsCount - 2)
    }

}

@Composable
fun rememberIndicatorState(initialSelectedDotIndex: Int = 0, dots: List<Dot>): IndicatorState {
    val state = remember {
        IndicatorState(initialSelectedDotIndex, dots)
    }
    return state
}

@Composable
fun Indicator2(
    modifier: Modifier = Modifier,
    state: IndicatorState,
    selectedDotSize: Dp = 17.dp,
    spacing: Dp = selectedDotSize / 2,
) {
    var width by remember {
        mutableIntStateOf(0)
    }
    Layout(
        modifier = modifier
            .onGloballyPositioned {
                width = it.size.width
            },
        content = {
            state.totalDots.forEachIndexed { index, dot ->
                val distance = abs(index - state.currentDot)
                val percentage = getDistancePercentage(distance,7)
                // We only animate the selected dot
                val animatedSize by animateDpAsState(
                    targetValue = if (state.currentDot == index) selectedDotSize else selectedDotSize / 2,
                    animationSpec = tween(150, easing = LinearEasing), label = "",
                )
                Dot(
                    size = if (state.currentDot == index) animatedSize else selectedDotSize * percentage,
                    color = dot.color
                )
            }
        },
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val height = selectedDotSize.roundToPx()

            layout(
                width = constraints.maxWidth,
                height = height
            ) {
                val maxItemWidth = placeables.maxOfOrNull { it.width } ?: 0
                val originPosition = constraints.maxWidth.div(2f) - maxItemWidth.div(2)

                //Left dots
                var leftItemsWidth = 0
                placeables.subList(0, state.currentDot).asReversed()
                    .forEachIndexed { distance, placeable ->
                        val yPosition = height / 2 - placeable.height / 2
                        val xPosition =
                            originPosition.roundToInt() - spacing.roundToPx() - placeable.width - leftItemsWidth
                        if (xPosition + placeable.width >= 0) {
                            placeable.place(x = xPosition, y = yPosition)
                        }
                        leftItemsWidth += placeable.width + spacing.roundToPx()
                    }

                //Middle dot
                placeables[state.currentDot].place(originPosition.roundToInt(), 0)

                //Right dots
                var rightItemsWidth = 0
                placeables.subList(state.currentDot + 1, placeables.size)
                    .forEachIndexed { distance, placeable ->
                        val yPosition = height / 2 - placeable.height / 2
                        val xPosition =
                            originPosition.roundToInt() + spacing.roundToPx() + placeable.width + rightItemsWidth + (selectedDotSize.roundToPx() - placeable.width)
                        if (xPosition - placeable.width <= width) {
                            placeable.place(x = xPosition, y = yPosition)
                        }
                        rightItemsWidth += placeable.width + spacing.roundToPx()
                    }
            }
        }
    )
}
