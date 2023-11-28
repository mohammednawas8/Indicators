package com.loc.indicators.indicator2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class IndicatorState {
    var selectedItemIndex: Int by mutableStateOf(0)
    
    fun moveNext() {
        selectedItemIndex++
    }

    fun movePrevious() {
        selectedItemIndex--
    }
}