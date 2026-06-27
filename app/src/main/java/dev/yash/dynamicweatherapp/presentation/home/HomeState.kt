package dev.yash.dynamicweatherapp.presentation.home

import dev.yash.dynamicweatherapp.domain.model.WeatherInfo

data class HomeState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationName: String = "Locating..." // Default text while fetching GPS
)