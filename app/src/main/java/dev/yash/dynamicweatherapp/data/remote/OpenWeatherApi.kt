package dev.yash.dynamicweatherapp.data.remote

import dev.yash.dynamicweatherapp.data.remote.dto.OneCallResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    // The One Call 3.0 endpoint provides current, minute, hourly, and daily forecast data
    @GET("data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("exclude") exclude: String = "minutely,alerts", // We don't need minutely data or alerts for our UI
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): OneCallResponseDto
}