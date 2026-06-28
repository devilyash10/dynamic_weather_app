package dev.yash.dynamicweatherapp.presentation.home

import dev.yash.dynamicweatherapp.domain.model.WeatherInfo
import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit


data class HomeState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null, // Or your specific Weather model type
    val error: String? = null,
    val locationName: String = "Locating...", // Default text while fetching GPS
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val hasAcceptedPrivacyPolicy: Boolean? = null
)