package dev.yash.dynamicweatherapp.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.yash.dynamicweatherapp.presentation.common.EmptyStateView
import dev.yash.dynamicweatherapp.presentation.home.components.DailyForecastSection
import dev.yash.dynamicweatherapp.presentation.home.components.HourlyForecastRow
import dev.yash.dynamicweatherapp.presentation.home.components.LocationHeader
import dev.yash.dynamicweatherapp.presentation.home.components.MainTemperatureDisplay
import dev.yash.dynamicweatherapp.presentation.home.components.WeatherMetricsGrid
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusDark

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
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
            )
        )
    }

    // 3. Standard UI Rendering
    // We use a Box as the root so Loading and Error states can perfectly center themselves
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NimbusDark)
            .statusBarsPadding()
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = NimbusAccentBlue
                )
            }

            state.error != null -> {
                // Implementing our new premium Empty State UI
                EmptyStateView(
                    title = "Data Unavailable",
                    message = state.error ?: "We couldn't retrieve the weather data. Please check your connection.",
                    onRetry = {
                        // Tells the ViewModel to try fetching the data again
                        viewModel.loadWeatherInfo()
                    }
                )
            }

            state.weatherInfo != null -> {
                // The scrollable Column is now only active when we actually have weather data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
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
}