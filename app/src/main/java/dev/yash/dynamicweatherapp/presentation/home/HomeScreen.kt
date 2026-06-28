package dev.yash.dynamicweatherapp.presentation.home

import android.Manifest // Make sure this is android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.yash.dynamicweatherapp.presentation.home.components.DailyForecastSection
import dev.yash.dynamicweatherapp.presentation.home.components.HourlyForecastRow
import dev.yash.dynamicweatherapp.presentation.home.components.LocationHeader
import dev.yash.dynamicweatherapp.presentation.home.components.MainTemperatureDisplay
import dev.yash.dynamicweatherapp.presentation.home.components.WeatherMetricsGrid
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusDark
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // 1. Setup the native permission request launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Check if either precise or approximate location was granted
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (isGranted) {
            // The moment they click "Allow", tell the ViewModel to fetch the weather!
            viewModel.loadWeatherInfo()
        }
    }

    // 2. Trigger the popup exactly once when the Home screen is first opened
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // 3. Standard UI Rendering
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NimbusDark)
            .statusBarsPadding() // NEW: Automatically pushes content below the notch/status bar
            .padding(top = 16.dp, start = 24.dp, end = 24.dp) // Removed the hardcoded 32.dp top padding
            .verticalScroll(rememberScrollState())
    ) {
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NimbusAccentBlue)
                }
            }
            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = state.error!!,
                        color = NimbusTextHint,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            state.weatherInfo != null -> {
                LocationHeader(
                    locationName = state.locationName,
                    lastUpdated = "Just updated"
                )

                Spacer(modifier = Modifier.height(32.dp))

                MainTemperatureDisplay(
                    currentWeather = state.weatherInfo!!.current,
                    temperatureUnit = state.temperatureUnit
                )

                Spacer(modifier = Modifier.height(48.dp))

                HourlyForecastRow(
                    hourlyForecasts = state.weatherInfo!!.hourly,
                    temperatureUnit = state.temperatureUnit
                )

                Spacer(modifier = Modifier.height(24.dp))

                WeatherMetricsGrid(currentWeather = state.weatherInfo!!.current)

                Spacer(modifier = Modifier.height(24.dp))

                DailyForecastSection(
                    dailyForecasts = state.weatherInfo!!.daily,
                    temperatureUnit = state.temperatureUnit
                )

                // Add bottom padding so the last item isn't hidden behind the Bottom Navigation Bar
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}