package com.loc.indicators.indicator

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.toSize
import kotlin.math.abs

data class Dot(
    val color: Color,
    val name: String = "" // TODO remove it
)

data class DotSizes(
    val selectedDot: Dp = 15.dp,
    val unselectedDot: Dp = 13.dp,
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Indicator(
    modifier: Modifier = Modifier,
    dots: List<Dot>,
    dotSizes: DotSizes = DotSizes(),
    pagerState: PagerState,
    spacing: Dp = 5.dp
) {
    var indicatorWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    HorizontalPager(
        modifier = modifier
            .onGloballyPositioned {
                indicatorWidth = with(density) {
                    it.size.toSize().width.toDp()
                }
            }
            .clipToBounds(),
        state = pagerState,
        userScrollEnabled = false,
        verticalAlignment = Alignment.CenterVertically,
        pageSize = PageSize.Fixed(dotSizes.selectedDot),
        contentPadding = PaddingValues(horizontal = indicatorWidth.div(2)),
        pageSpacing = spacing,
    ) { currentIndex ->
        val distance = abs(currentIndex - pagerState.currentPage)
        Box(modifier = Modifier.size(dotSizes.selectedDot), contentAlignment = Alignment.Center) {
            val percentage = getDistancePercentage(distance)
            val unselectedDotSize = percentage * dotSizes.unselectedDot
            val size by animateDpAsState(
                targetValue = if (distance == 0) dotSizes.selectedDot else unselectedDotSize,
                label = "Dot size animation"
            )
            Dot(
                size = size,
                color = dots[currentIndex].color
            )
        }
    }
}

fun getDistancePercentage(distance: Int): Float {
    return 1f - distance.toFloat() / 10f
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