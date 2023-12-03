package com.loc.indicators

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.loc.indicators.indicator.Indicator
import com.loc.indicators.indicator2.Dot
import com.loc.indicators.indicator2.Indicator2
import com.loc.indicators.indicator2.rememberIndicatorState
import com.loc.indicators.indicator3.Indicator3
import com.loc.indicators.indicator3.rememberIndicator3State
import com.loc.indicators.ui.theme.IndicatorsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dots = (1..3000).map { Dot(Color.Gray,"$it") }
        setContent {
            IndicatorsTheme {
                Indicator3Sample(dots = dots)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Indicator1Sample(dots: List<Dot>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val scope = rememberCoroutineScope()
        val pagerState= rememberPagerState {
            dots.size
        }
        TextButton(onClick = {
            scope.launch {
                pagerState.animateScrollToPage(
                    pagerState.currentPage - 1
                )
            }
        }) {
            Text(text = "Previous")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${pagerState.currentPage + 1} of ${dots.size}")
            Spacer(modifier = Modifier.height(5.dp))

            Indicator(
                dots = dots,
                pagerState = pagerState
            )
        }

        TextButton(onClick = {
            scope.launch {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1
                )
            }
        }) {
            Text(text = "Next")
        }
    }
}


@Composable
fun Indicator2Sample(dots: List<Dot>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val state = rememberIndicatorState(dots = dots)
        TextButton(
            onClick = {
            state.movePrevious()
        }) {
            Text(text = "Previous")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${state.currentDot + 1} of ${dots.size}")
            Spacer(modifier = Modifier.height(5.dp))

            Indicator2(
                modifier = Modifier.padding(horizontal = 15.dp),
                state = state
            )
        }

        TextButton(onClick = {
            state.moveNext()
        }) {
            Text(text = "Next")
        }
    }
}

@Composable
fun Indicator3Sample(dots: List<Dot>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val state = rememberIndicator3State(dots = dots, initialSelectedDotIndex = 0)
        val scope = rememberCoroutineScope()
        TextButton(
            onClick = {
                scope.launch {
                    state.movePrevious()
                }
            }) {
            Text(text = "Previous")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${state.currentDot + 1} of ${dots.size}")
            Spacer(modifier = Modifier.height(5.dp))

            Indicator3(
                modifier = Modifier.padding(horizontal = 0.dp),
                state = state,

            )
        }
        TextButton(onClick = {
            scope.launch {
                state.moveNext()
            }
        }) {
            Text(text = "Next")
        }
    }
}