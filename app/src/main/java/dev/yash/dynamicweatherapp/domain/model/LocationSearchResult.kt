package dev.yash.dynamicweatherapp.domain.model

data class LocationSearchResult(
    val id: Int,
    val name: String,
    val country: String,
    val admin1: String?,
    val latitude: Double,
    val longitude: Double
)