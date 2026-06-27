package dev.yash.dynamicweatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenMeteoDto(
    val current: CurrentDto,
    val hourly: HourlyDto,
    val daily: DailyDto
)

data class CurrentDto(
    val time: Long,
    val temperature_2m: Double,
    val relative_humidity_2m: Int,
    val apparent_temperature: Double,
    val is_day: Int,
    val weather_code: Int,
    val wind_speed_10m: Double,
    val surface_pressure: Double
)

data class HourlyDto(
    val time: List<Long>,
    val temperature_2m: List<Double>,
    val weather_code: List<Int>
)

data class DailyDto(
    val time: List<Long>,
    val weather_code: List<Int>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val uv_index_max: List<Double>
)