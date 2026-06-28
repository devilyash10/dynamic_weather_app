package dev.yash.dynamicweatherapp.domain.settings

import kotlinx.coroutines.flow.Flow

// A clean Enum to represent our temperature units
enum class TemperatureUnit {
    CELSIUS, FAHRENHEIT
}

interface SettingsRepository {
    // 1. Temperature Blueprints
    val temperatureUnit: Flow<TemperatureUnit>
    suspend fun setTemperatureUnit(unit: TemperatureUnit)

    // 2. Background Sync Blueprints
    val syncInterval: Flow<Long>
    suspend fun setSyncInterval(minutes: Long)

    val hasAcceptedPrivacyPolicy: Flow<Boolean>
    suspend fun setPrivacyPolicyAccepted(accepted: Boolean)
}