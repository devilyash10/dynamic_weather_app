package dev.yash.dynamicweatherapp.presentation.common

import dev.yash.dynamicweatherapp.R

fun getWeatherIconResource(weatherCode: String): Int {
    return when (weatherCode) {
        // ==========================================
        // 1. OPEN-WEATHER DAY CODES ("d")
        // ==========================================
        "01d" -> R.drawable.ic_sunny
        "02d" -> R.drawable.ic_partly_cloudy
        "03d", "04d" -> R.drawable.ic_cloudy
        "09d" -> R.drawable.ic_heavy_rain
        "10d" -> R.drawable.ic_rain
        "11d" -> R.drawable.ic_thunderstorm
        "13d" -> R.drawable.ic_snow
        "50d" -> R.drawable.ic_sun_mist

        // ==========================================
        // 2. OPEN-WEATHER NIGHT CODES ("n")
        // ==========================================
        "01n" -> R.drawable.ic_moon // Uses your custom moon icon!
        "02n" -> R.drawable.ic_partly_cloudy
        "03n", "04n" -> R.drawable.ic_cloudy
        "09n" -> R.drawable.ic_heavy_rain
        "10n" -> R.drawable.ic_rain
        "11n" -> R.drawable.ic_thunderstorm
        "13n" -> R.drawable.ic_snow
        "50n" -> R.drawable.ic_moon_mist // Uses your custom moon mist icon!

        // ==========================================
        // 3. OPEN-METEO WMO INTEGER CODES (Fallback)
        // ==========================================
        "0" -> R.drawable.ic_sunny
        "1", "2" -> R.drawable.ic_partly_cloudy
        "3" -> R.drawable.ic_cloudy
        "45" -> R.drawable.ic_sun_mist
        "48" -> R.drawable.ic_moon_mist
        "51", "53", "55", "61", "63", "80", "81" -> R.drawable.ic_rain
        "65", "82" -> R.drawable.ic_heavy_rain
        "56", "57", "66", "67", "71", "73", "75", "77", "85", "86" -> R.drawable.ic_snow
        "95", "96", "99" -> R.drawable.ic_thunderstorm

        // Fallback for missing/unknown data
        else -> R.drawable.ic_sunny
    }
}