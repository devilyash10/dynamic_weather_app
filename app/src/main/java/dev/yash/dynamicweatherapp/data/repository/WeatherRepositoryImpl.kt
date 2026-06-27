package dev.yash.dynamicweatherapp.data.repository

import dev.yash.dynamicweatherapp.data.mapper.toWeatherInfo
import dev.yash.dynamicweatherapp.data.remote.OpenMeteoApi
import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult
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

    override suspend fun searchLocation(query: String): Result<List<LocationSearchResult>> {
        return try {
            val response = api.searchLocation(query = query)

            // Map the DTOs to our Domain models, or return an empty list if null
            val results = response.results?.map { dto ->
                LocationSearchResult(
                    id = dto.id,
                    name = dto.name,
                    country = dto.country,
                    admin1 = dto.admin1,
                    latitude = dto.latitude,
                    longitude = dto.longitude
                )
            } ?: emptyList()

            Result.success(results)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}