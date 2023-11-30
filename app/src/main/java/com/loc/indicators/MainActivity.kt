package com.loc.indicators

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.loc.indicators.indicator.Dot
import com.loc.indicators.indicator.Indicator
import com.loc.indicators.indicator2.Indicator2
import com.loc.indicators.ui.theme.IndicatorsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dots = listOf(
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
            Dot(Color.Gray,"0"), Dot(Color.Gray,"1"), Dot(Color.Gray,"2"),
        )
        setContent {
            IndicatorsTheme {
                val pagerState = rememberPagerState { dots.size }
                var selectedItem by remember {
                    mutableStateOf(0)
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val scope = rememberCoroutineScope()
                    TextButton(onClick = {
                        scope.launch {
                            selectedItem = (selectedItem - 1).coerceIn(0, dots.size - 1)

                        }
                    }) {
                        Text(text = "Previous")
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "${selectedItem + 1} of ${dots.size}")
                        Spacer(modifier = Modifier.height(5.dp))

                        Indicator2(
                            dots = dots,
                            selectedDotSize = 15.dp,
                            selectedDotIndex = selectedItem
                        )
                    }

                    TextButton(onClick = {
                        scope.launch {
                            selectedItem = (selectedItem + 1).coerceIn(0, dots.size - 1)
                        }
                    }) {
                        Text(text = "Next")
                    }
                }
            }
        }
    }
}



