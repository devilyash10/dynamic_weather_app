package dev.yash.dynamicweatherapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dev.yash.dynamicweatherapp.domain.settings.SettingsRepository
import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private companion object {
        // We use a Boolean: true = Celsius, false = Fahrenheit
        val IS_CELSIUS_KEY = booleanPreferencesKey("is_celsius")
    }

    override val temperatureUnit: Flow<TemperatureUnit> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Default to Celsius if the user hasn't set anything yet
            val isCelsius = preferences[IS_CELSIUS_KEY] ?: true
            if (isCelsius) TemperatureUnit.CELSIUS else TemperatureUnit.FAHRENHEIT
        }

    override suspend fun setTemperatureUnit(unit: TemperatureUnit) {
        dataStore.edit { preferences ->
            preferences[IS_CELSIUS_KEY] = unit == TemperatureUnit.CELSIUS
        }
    }
}