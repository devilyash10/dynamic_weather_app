package dev.yash.dynamicweatherapp.domain.repository

import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult
import dev.yash.dynamicweatherapp.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo>

    suspend fun searchLocation(query: String): Result<List<LocationSearchResult>>
}
