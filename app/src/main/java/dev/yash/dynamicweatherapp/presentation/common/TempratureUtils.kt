package dev.yash.dynamicweatherapp.presentation.common

import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit
import kotlin.math.roundToInt

// Converts Celsius to Fahrenheit if needed, and formats it as a string
fun Double.toFormattedTemp(unit: TemperatureUnit): String {
    val convertedTemp = if (unit == TemperatureUnit.FAHRENHEIT) {
        (this * 9 / 5) + 32
    } else {
        this
    }
    return "${convertedTemp.roundToInt()}°"
}

// Returns just the 'C' or 'F' string
fun TemperatureUnit.getSymbol(): String {
    return if (this == TemperatureUnit.FAHRENHEIT) "F" else "C"
}