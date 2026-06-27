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
    private val repository: SettingsRepository
) : ViewModel() {

    // stateIn converts the standard Flow from our Repository into a StateFlow
    // that Compose can easily observe. It defaults to CELSIUS while loading.
    val temperatureUnit: StateFlow<TemperatureUnit> = repository.temperatureUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TemperatureUnit.CELSIUS
        )

    fun toggleTemperatureUnit(isCelsius: Boolean) {
        viewModelScope.launch {
            val unit = if (isCelsius) TemperatureUnit.CELSIUS else TemperatureUnit.FAHRENHEIT
            repository.setTemperatureUnit(unit)
        }
    }
}