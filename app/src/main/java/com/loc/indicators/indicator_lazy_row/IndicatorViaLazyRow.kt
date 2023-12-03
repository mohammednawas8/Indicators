package com.loc.indicators.indicator_lazy_row

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.toSize
import com.loc.indicators.Dot
import com.loc.indicators.getDistancePercentage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs

class IndicatorViaLazyRowState(
    initialSelectedDotIndex: Int,
    val totalDots: List<Dot>,
    val lazyListState: LazyListState
) {
    var currentDot by mutableIntStateOf(initialSelectedDotIndex)

    private var job: Job

    init {
        job = CoroutineScope(Dispatchers.Main).launch {
            lazyListState.scrollToItem(initialSelectedDotIndex)
        }
    }

    suspend fun moveNext() {
        val nextDot = (currentDot + 1).coerceIn(1, totalDots.size - 1)
        currentDot = nextDot
        lazyListState.animateScrollToItem(nextDot)
    }

    suspend fun movePrevious() {
        val previousDot = (currentDot - 1).coerceIn(0, totalDots.size - 2)
        currentDot = previousDot
        lazyListState.animateScrollToItem(previousDot)
    }

    protected fun finalize() {
        job.cancel()
    }
}

@Composable
fun rememberIndicatorViaLazyRowState(
    initialSelectedDotIndex: Int = 0,
    dots: List<Dot>,
    lazyListState: LazyListState = rememberLazyListState()
): IndicatorViaLazyRowState {
    val state = remember {
        IndicatorViaLazyRowState(initialSelectedDotIndex, dots, lazyListState)
    }
    return state
}

@Composable
fun IndicatorViaLazyRow(
    modifier: Modifier = Modifier,
    selectedDotSize: Dp = 17.dp,
    state: IndicatorViaLazyRowState,
    spacing: Dp = 8.dp
) {
    val animationDuration = 200
    var horizontalContentPadding by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    LazyRow(
        modifier = modifier
            .height(selectedDotSize)
            .onGloballyPositioned {
                with(density) {
                    horizontalContentPadding = it.size.toSize().width.toDp().div(2) - selectedDotSize.div(2)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        contentPadding = PaddingValues(horizontal = horizontalContentPadding),
        state = state.lazyListState
    ) {
        itemsIndexed(state.totalDots) { index, dot ->
            val distance = abs(index - state.currentDot)
            val percentage = getDistancePercentage(distance, 6)
            val animatedSize by animateDpAsState(
                targetValue = if (state.currentDot == index) selectedDotSize else selectedDotSize / 2,
                animationSpec = tween(animationDuration), label = "",
            )
            if (state.currentDot == index) {
                Box(
                    modifier = Modifier.size(selectedDotSize),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Dot(
                        size = animatedSize,
                        color = dot.color
                    )
                }
            } else {
                Dot(
                    size = percentage * selectedDotSize,
                    color = dot.color
                )
            }
        }
    }
}