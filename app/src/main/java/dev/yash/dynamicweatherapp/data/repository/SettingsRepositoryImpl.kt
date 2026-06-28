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
import androidx.datastore.preferences.core.longPreferencesKey

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    // 1. Define the key
    private val SYNC_INTERVAL_KEY = longPreferencesKey("sync_interval")

    // 2. Implement the Flow to read the data
    override val syncInterval: Flow<Long> = dataStore.data.map { preferences ->
        preferences[SYNC_INTERVAL_KEY] ?: 15L // Defaults to 15 if not set
    }

    // 3. Implement the suspend function to save the data
    override suspend fun setSyncInterval(minutes: Long) {
        dataStore.edit { preferences ->
            preferences[SYNC_INTERVAL_KEY] = minutes
        }
    }

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

    private val PRIVACY_POLICY_KEY = booleanPreferencesKey("privacy_policy_accepted")

    override val hasAcceptedPrivacyPolicy: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[PRIVACY_POLICY_KEY] ?: false // Default to false for first-time users!
    }

    override suspend fun setPrivacyPolicyAccepted(accepted: Boolean) {
        dataStore.edit { prefs ->
            prefs[PRIVACY_POLICY_KEY] = accepted
        }
    }
}