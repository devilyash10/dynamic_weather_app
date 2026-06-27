package dev.yash.dynamicweatherapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.yash.dynamicweatherapp.data.local.entity.SavedLocationEntity
import dev.yash.dynamicweatherapp.domain.model.LocationSearchResult
import dev.yash.dynamicweatherapp.presentation.common.GlassCard
import dev.yash.dynamicweatherapp.presentation.navigation.Screen
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusDark
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NimbusDark)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Search City",
            style = MaterialTheme.typography.headlineMedium,
            color = NimbusTextWhite
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("E.g., London, Tokyo, New York", color = NimbusTextHint) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = NimbusTextHint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = NimbusTextWhite,
                unfocusedTextColor = NimbusTextWhite,
                cursorColor = NimbusAccentBlue,
                focusedBorderColor = NimbusAccentBlue,
                unfocusedBorderColor = NimbusTextHint.copy(alpha = 0.5f),
                focusedContainerColor = Color.White.copy(alpha = 0.05f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.05f)
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        when {
            state.isSearching -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    CircularProgressIndicator(color = NimbusAccentBlue)
                }
            }
            state.searchQuery.length >= 2 && state.error != null -> {
                Text(text = state.error!!, color = NimbusTextHint, modifier = Modifier.padding(16.dp))
            }
            state.searchQuery.length >= 2 && state.searchResults.isNotEmpty() -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(state.searchResults) { result ->
                        SearchResultItem(
                            location = result,
                            onClick = {
                                viewModel.saveLocation(result)
                                navController.navigate(Screen.Home.passCoordinates(result.latitude, result.longitude, result.name)) {
                                    popUpTo(Screen.Home.route) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
            else -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "SAVED LOCATIONS",
                        style = MaterialTheme.typography.titleMedium,
                        color = NimbusTextHint
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.savedLocations.isEmpty()) {
                        Text(
                            text = "No saved locations yet. Search for cities above to add them.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NimbusTextHint,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 100.dp)
                        ) {
                            items(state.savedLocations) { dbLocation ->
                                SavedLocationRow(
                                    location = dbLocation,
                                    onDeleteClick = { viewModel.deleteLocation(dbLocation) },
                                    onClick = {
                                        navController.navigate(Screen.Home.passCoordinates(dbLocation.latitude, dbLocation.longitude, dbLocation.name)) {
                                            popUpTo(Screen.Home.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(location: LocationSearchResult, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = NimbusAccentBlue, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = location.name, style = MaterialTheme.typography.titleMedium, color = NimbusTextWhite)
                val subtitle = if (!location.admin1.isNullOrEmpty()) "${location.admin1}, ${location.country}" else location.country
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = NimbusTextHint)
            }
        }
    }
}

@Composable
fun SavedLocationRow(
    location: SavedLocationEntity,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit // Added missing click handling parameter
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() } // Connected row selection click layout logic
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = NimbusAccentBlue, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = location.name, style = MaterialTheme.typography.titleMedium, color = NimbusTextWhite)
                    val subtitle = if (!location.admin1.isNullOrEmpty()) "${location.admin1}, ${location.country}" else location.country
                    Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = NimbusTextHint)
                }
            }

            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Location", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}