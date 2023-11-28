package com.loc.indicators

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.loc.indicators.indicator1.Indicator1Sample
import com.loc.indicators.indicator2.Indicator2Sample
import com.loc.indicators.ui.theme.IndicatorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndicatorsTheme {
                Indicator1Sample()
            }
        }
    }
}


