package com.example.currencyconverter.common

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.round(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}
