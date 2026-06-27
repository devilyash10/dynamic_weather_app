package dev.yash.dynamicweatherapp.data.remote.dto

data class GeocodingResponseDto(
    val results: List<GeocodingResultDto>?
)

data class GeocodingResultDto(
    val id: Int,
    val name: String,
    val country: String,
    val admin1: String?,
    val latitude: Double,
    val longitude: Double
)