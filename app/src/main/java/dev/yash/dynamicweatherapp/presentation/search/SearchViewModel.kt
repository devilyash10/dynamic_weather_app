package dev.yash.dynamicweatherapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.dynamicweatherapp.data.local.dao.LocationDao
import dev.yash.dynamicweatherapp.data.local.entity.SavedLocationEntity
import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Job
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
    private val locationDao: LocationDao // NEW: Inject Room DAO
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        // NEW: Continuously observe the Room Database.
        // If an item is added, this flow emits automatically and updates the UI.
        viewModelScope.launch {
            locationDao.getSavedLocations().collect { savedList ->
                _state.update { it.copy(savedLocations = savedList) }
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

    // NEW: Function to save a search result into the local database
    fun saveLocation(location: LocationSearchResult) {
        viewModelScope.launch {
            // Prevent duplicates by checking if coordinates already exist
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
                // Clear search bar and results once saved successfully
                _state.update { it.copy(searchQuery = "", searchResults = emptyList()) }
            }
        }
    }

    // NEW: Function to delete a city from the database if they want to remove it
    fun deleteLocation(entity: SavedLocationEntity) {
        viewModelScope.launch {
            locationDao.deleteLocation(entity)
        }
    }
}