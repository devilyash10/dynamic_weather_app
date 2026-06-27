package dev.yash.dynamicweatherapp.domain.repository

import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult
import dev.yash.dynamicweatherapp.domain.model.WeatherInfo

interface WeatherRepository {

    // We use Kotlin's built-in Result class to elegantly handle Success/Failure states
    suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo>

    suspend fun searchLocation(query: String): Result<List<LocationSearchResult>>
}
