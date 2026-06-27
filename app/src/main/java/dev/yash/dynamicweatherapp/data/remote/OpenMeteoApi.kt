package dev.yash.dynamicweatherapp.data.remote

import dev.yash.dynamicweatherapp.data.remote.dto.OpenMeteoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi { // Keeping the name so DI doesn't break, but it's hitting Open-Meteo!

    @GET("v1/forecast")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        // We explicitly tell it exactly what data points we need for our UI
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,weather_code,wind_speed_10m,surface_pressure",
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max",
        @Query("timezone") timezone: String = "auto", // Automatically syncs to the user's local time
        @Query("timeformat") timeformat: String = "unixtime" // Matches our existing UI logic perfectly
    ): OpenMeteoDto
}