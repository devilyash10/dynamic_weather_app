package dev.yash.dynamicweatherapp.data.mapper

import dev.yash.dynamicweatherapp.data.remote.dto.OpenMeteoDto
import dev.yash.dynamicweatherapp.domain.model.CurrentWeather
import dev.yash.dynamicweatherapp.domain.model.DailyForecast
import dev.yash.dynamicweatherapp.domain.model.HourlyForecast
import dev.yash.dynamicweatherapp.domain.model.WeatherInfo

fun OpenMeteoDto.toWeatherInfo(): WeatherInfo {
    // 1. Map Current Weather
    val current = CurrentWeather(
        temperature = current.temperature_2m,
        feelsLike = current.apparent_temperature,
        humidity = current.relative_humidity_2m,
        windSpeed = current.wind_speed_10m,
        pressure = current.surface_pressure.toInt(),
        uvIndex = daily.uv_index_max.firstOrNull() ?: 0.0, // Grab today's UV index
        sunriseTime = daily.sunrise.firstOrNull() ?: 0L,
        sunsetTime = daily.sunset.firstOrNull() ?: 0L,
        condition = parseWeatherCodeToCondition(current.weather_code),
        iconId = parseWeatherCodeToIcon(current.weather_code, current.is_day == 1)
    )

    // 2. Map Hourly Forecast
    val currentTime = System.currentTimeMillis() / 1000
    val hourlyList = mutableListOf<HourlyForecast>()
    for (i in hourly.time.indices) {
        val time = hourly.time[i]
        // Open-Meteo returns data starting from midnight. We only want future hours.
        if (time >= currentTime) {
            hourlyList.add(
                HourlyForecast(
                    time = time,
                    temperature = hourly.temperature_2m[i],
                    iconId = parseWeatherCodeToIcon(hourly.weather_code[i], isDay = true)
                )
            )
        }
    }

    // 3. Map Daily Forecast
    val dailyList = mutableListOf<DailyForecast>()
    for (i in daily.time.indices) {
        dailyList.add(
            DailyForecast(
                time = daily.time[i],
                minTemp = daily.temperature_2m_min[i],
                maxTemp = daily.temperature_2m_max[i],
                iconId = parseWeatherCodeToIcon(daily.weather_code[i], isDay = true)
            )
        )
    }

    return WeatherInfo(
        current = current,
        hourly = hourlyList,
        daily = dailyList
    )
}

// Maps standard WMO codes to our existing UI strings
private fun parseWeatherCodeToCondition(code: Int): String {
    return when (code) {
        0 -> "Clear sky"
        1, 2, 3 -> "Partly cloudy"
        45, 48 -> "Fog"
        51, 53, 55, 56, 57 -> "Drizzle"
        61, 63, 65, 66, 67 -> "Rain"
        71, 73, 75, 77 -> "Snow"
        95, 96, 99 -> "Thunderstorm"
        else -> "Unknown"
    }
}

// Maps WMO codes to the "01d", "10n" format your Phase 4 UI expects
private fun parseWeatherCodeToIcon(code: Int, isDay: Boolean): String {
    val suffix = if (isDay) "d" else "n"
    return when (code) {
        0 -> "01$suffix" // Clear
        1, 2 -> "02$suffix" // Partly Cloudy
        3 -> "03$suffix" // Overcast
        45, 48 -> "50$suffix" // Fog
        51, 53, 55, 56, 57 -> "09$suffix" // Drizzle
        61, 63, 65, 66, 67 -> "10$suffix" // Rain
        71, 73, 75, 77 -> "13$suffix" // Snow
        95, 96, 99 -> "11$suffix" // Thunderstorm
        else -> "01$suffix"
    }
}