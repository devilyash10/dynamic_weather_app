package dev.yash.dynamicweatherapp.domain.settings

import kotlinx.coroutines.flow.Flow

// A clean Enum to represent our temperature units
enum class TemperatureUnit {
    CELSIUS, FAHRENHEIT
}

interface SettingsRepository {
    // Flow allows the UI to instantly update if the setting changes
    val temperatureUnit: Flow<TemperatureUnit>

    suspend fun setTemperatureUnit(unit: TemperatureUnit)
}