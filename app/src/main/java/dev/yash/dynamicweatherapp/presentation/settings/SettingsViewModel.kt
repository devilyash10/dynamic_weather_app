package dev.yash.dynamicweatherapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.dynamicweatherapp.domain.settings.SettingsRepository
import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    // 1. Automatically grab the current preference from DataStore
    val temperatureUnit: StateFlow<TemperatureUnit> = settingsRepository.temperatureUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TemperatureUnit.CELSIUS // Default value before loading
        )

    // 2. The function the UI calls when the user taps the switch
    fun toggleTemperatureUnit() {
        viewModelScope.launch {
            val currentUnit = temperatureUnit.value
            val newUnit = if (currentUnit == TemperatureUnit.CELSIUS) {
                TemperatureUnit.FAHRENHEIT
            } else {
                TemperatureUnit.CELSIUS
            }
            // Save it permanently to the device
            settingsRepository.setTemperatureUnit(newUnit)
        }
    }
}