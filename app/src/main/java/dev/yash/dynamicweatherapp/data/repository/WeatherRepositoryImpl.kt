package dev.yash.dynamicweatherapp.data.repository

import dev.yash.dynamicweatherapp.data.remote.OpenWeatherApi
import dev.yash.dynamicweatherapp.domain.model.WeatherInfo
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: OpenWeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo> {
        return try {
            // TODO: In Phase 3/7, we will move the API key to local.properties or BuildConfig
            // to keep it out of version control. For now, place your actual string key here.
            val apiKey = "YOUR_OPEN_WEATHER_API_KEY_HERE"

            val response = api.getWeatherData(
                lat = lat,
                long = long,
                apiKey = apiKey
            )

            // Look how clean this is thanks to our mapper!
            Result.success(response.toWeatherInfo())

        } catch (e: Exception) {
            // This catches network timeouts, parsing errors, or offline issues
            e.printStackTrace()
            Result.failure(e)
        }
    }
}