package dev.yash.dynamicweatherapp.presentation.common

import dev.yash.dynamicweatherapp.R

// A helper function to map OpenWeather icon IDs to local drawable resources.
// NOTE: You will need to add actual image files to res/drawable named ic_sunny, ic_cloudy, etc.
// For now, these R.drawable references might show in red until you add the images,
// or you can replace them with a default launcher icon just to get it compiling.
fun getWeatherIconResource(iconId: String): Int {
    return when (iconId) {
        "01d" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_sunny
        "01n" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_moon
        "02d", "03d", "04d" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_cloudy_day
        "02n", "03n", "04n" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_cloudy_night
        "09d", "09n", "10d", "10n" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_rain
        "11d", "11n" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_thunderstorm
        "13d", "13n" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_snow
        "50d", "50n" -> R.drawable.ic_launcher_foreground // TODO: Replace with ic_mist
        else -> R.drawable.ic_launcher_foreground // Fallback icon
    }
}