package dev.yash.dynamicweatherapp.data.repository

import dev.yash.dynamicweatherapp.data.mapper.toWeatherInfo
import dev.yash.dynamicweatherapp.data.remote.OpenMeteoApi
import dev.yash.dynamicweatherapp.domain.model.WeatherInfo
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: OpenMeteoApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo> {
        return try {
            // No API Key needed for Open-Meteo!
            val response = api.getWeatherData(
                lat = lat,
                long = long
            )

            Result.success(response.toWeatherInfo())

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}