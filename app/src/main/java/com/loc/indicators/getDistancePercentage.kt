package com.loc.indicators



fun getDistancePercentage(distance: Int, factor: Int = 6): Float {
//    return when (distance) {
//        in 0..3 -> 0.7f
//        4 -> 0.5f
//        5 -> 0.35f
//        6 -> 0.2f
//        7 -> 0.1f
//        else -> 0f
//    }
    return 1f - distance / (factor + 1f)
}
