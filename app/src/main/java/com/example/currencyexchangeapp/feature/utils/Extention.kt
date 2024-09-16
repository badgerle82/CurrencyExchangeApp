package com.example.currencyexchangeapp.feature.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.roundToTwoDecimalPlaces(): Double {
    return BigDecimal(this)
        .setScale(2, RoundingMode.HALF_UP)  // Rounds up if the next digit is 5 or higher
        .toDouble()
}