package com.loc.indicators.indicator2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.loc.indicators.Dot

@Composable
fun Indicator2Sample() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        val dots = listOf(
            Dot(Color.Red),
            Dot(Color.Gray),
            Dot(Color.Gray),
            Dot(Color.Gray),
            Dot(Color.Black),
            Dot(Color.Red),
            Dot(Color.Gray),
            Dot(Color.Gray),
            Dot(Color.Gray),
            Dot(Color.Black),
            Dot(Color.Red),
        )
        Text(text = "${selectedItemIndex + 1} of ${dots.size}")
        Spacer(modifier = Modifier.height(10.dp))
//        Indicator2(
//            modifier = Modifier,
//            totalDots = dots,
//            selectedIndex = selectedItemIndex,
//        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = {
                selectedItemIndex = (selectedItemIndex - 1).coerceIn(0, dots.size - 1)
            }) {
                Text(text = "Previous")
            }
            TextButton(onClick = {
                selectedItemIndex = (selectedItemIndex + 1).coerceIn(0, dots.size - 1)
            }) {
                Text(text = "Next")
            }
        }
    }
}