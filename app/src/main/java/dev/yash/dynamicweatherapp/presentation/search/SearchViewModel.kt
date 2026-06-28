package dev.yash.dynamicweatherapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.dynamicweatherapp.data.local.dao.LocationDao
import dev.yash.dynamicweatherapp.data.local.entity.SavedLocationEntity
import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository
import dev.yash.dynamicweatherapp.domain.settings.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationDao: LocationDao,
    private val settingsRepository: SettingsRepository // NEW: Inject Settings
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        // 1. Observe Temperature Settings
        viewModelScope.launch {
            settingsRepository.temperatureUnit.collect { unit ->
                _state.update { it.copy(temperatureUnit = unit) }
            }
        }

        // 2. Observe Saved Locations and fetch live weather for each of them concurrently
        viewModelScope.launch {
            locationDao.getSavedLocations().collect { savedList ->
                val deferredList = savedList.map { location ->
                    async {
                        val weatherResult = repository.getWeatherData(location.latitude, location.longitude)
                        SavedLocationWeatherState(
                            location = location,
                            weatherInfo = weatherResult.getOrNull()
                        )
                    }
                }
                // Wait for all network calls to finish, then update the UI
                val weatherList = deferredList.awaitAll()
                _state.update { it.copy(savedLocations = weatherList) }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchJob?.cancel()

        if (query.length < 2) {
            _state.update { it.copy(searchResults = emptyList(), error = null, isSearching = false) }
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            _state.update { it.copy(isSearching = true, error = null) }

            val result = repository.searchLocation(query)
            result.fold(
                onSuccess = { locations ->
                    _state.update { it.copy(
                        searchResults = locations,
                        isSearching = false,
                        error = if (locations.isEmpty()) "No results found for '$query'" else null
                    ) }
                },
                onFailure = { error ->
                    _state.update { it.copy(
                        searchResults = emptyList(),
                        isSearching = false,
                        error = "Network error while searching."
                    ) }
                }
            )
        }
    }

    fun saveLocation(location: LocationSearchResult) {
        viewModelScope.launch {
            val existing = locationDao.getLocationByCoordinates(location.latitude, location.longitude)
            if (existing == null) {
                locationDao.insertLocation(
                    SavedLocationEntity(
                        name = location.name,
                        country = location.country,
                        admin1 = location.admin1,
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
                _state.update { it.copy(searchQuery = "", searchResults = emptyList()) }
            }
        }
    }

    fun deleteLocation(entity: SavedLocationEntity) {
        viewModelScope.launch {
            locationDao.deleteLocation(entity)
        }
    }
}