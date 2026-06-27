package dev.yash.dynamicweatherapp.data.repository

import dev.yash.dynamicweatherapp.data.remote.dto.CurrentWeatherDto
import dev.yash.dynamicweatherapp.data.remote.dto.DailyWeatherDto
import dev.yash.dynamicweatherapp.data.remote.dto.HourlyWeatherDto
import dev.yash.dynamicweatherapp.data.remote.dto.OneCallResponseDto
import dev.yash.dynamicweatherapp.domain.model.CurrentWeather
import dev.yash.dynamicweatherapp.domain.model.DailyForecast
import dev.yash.dynamicweatherapp.domain.model.HourlyForecast
import dev.yash.dynamicweatherapp.domain.model.WeatherInfo

// Extension to map the entire network response to our clean Domain model
fun OneCallResponseDto.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        current = current.toCurrentWeather(),
        hourly = hourly.map { it.toHourlyForecast() },
        daily = daily.map { it.toDailyForecast() }
    )
}

private fun CurrentWeatherDto.toCurrentWeather(): CurrentWeather {
    val weatherCondition = weather.firstOrNull()
    return CurrentWeather(
        temperature = temp,
        feelsLike = feelsLike,
        condition = weatherCondition?.main ?: "Unknown",
        iconId = weatherCondition?.icon ?: "",
        humidity = humidity,
        windSpeed = windSpeed,
        pressure = pressure,
        uvIndex = uvi,
        sunriseTime = sunrise,
        sunsetTime = sunset
    )
}

private fun HourlyWeatherDto.toHourlyForecast(): HourlyForecast {
    return HourlyForecast(
        time = dt,
        temperature = temp,
        iconId = weather.firstOrNull()?.icon ?: ""
    )
}

private fun DailyWeatherDto.toDailyForecast(): DailyForecast {
    return DailyForecast(
        time = dt,
        minTemp = temp.min,
        maxTemp = temp.max,
        iconId = weather.firstOrNull()?.icon ?: ""
    )
}