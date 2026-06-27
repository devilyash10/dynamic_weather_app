package dev.yash.dynamicweatherapp.presentation.search

import dev.yash.dynamicweatherapp.data.local.entity.SavedLocationEntity
import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult

data class SearchState(
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val searchResults: List<LocationSearchResult> = emptyList(),
    val savedLocations: List<SavedLocationEntity> = emptyList(), // NEW: Holds DB items
    val error: String? = null
)