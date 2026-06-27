package dev.yash.dynamicweatherapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.dynamicweatherapp.domain.location.LocationTracker
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository
import dev.yash.dynamicweatherapp.domain.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val settingsRepository: SettingsRepository // NEW: Inject settings
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        // NEW: Listen to DataStore in the background. If the user flips the switch
        // in Settings, this instantly updates the Home state.
        viewModelScope.launch {
            settingsRepository.temperatureUnit.collect { unit ->
                _state.update { it.copy(temperatureUnit = unit) }
            }
        }

        loadWeatherInfo()
    }

    fun loadWeatherInfo() {
        viewModelScope.launch {
            // 1. Set state to loading
            _state.update { it.copy(
                isLoading = true,
                error = null
            ) }

            // 2. Fetch the GPS coordinates
            locationTracker.getCurrentLocation()?.let { location ->

                // 3. If we have a location, fetch the weather from the repository
                val result = repository.getWeatherData(location.latitude, location.longitude)

                // 4. Handle the Success or Failure using Kotlin's fold function
                result.fold(
                    onSuccess = { weatherData ->
                        _state.update { it.copy(
                            weatherInfo = weatherData,
                            isLoading = false,
                            error = null,
                            locationName = "Current Location" // In a production app, use Geocoder to get city name
                        ) }
                    },
                    onFailure = { error ->
                        _state.update { it.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = error.message ?: "An unexpected network error occurred."
                        ) }
                    }
                )
            } ?: run {
                // Handle the case where GPS is disabled or permissions are denied
                _state.update { it.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                ) }
            }
        }
    }
}