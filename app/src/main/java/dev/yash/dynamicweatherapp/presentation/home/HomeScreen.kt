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
import dev.yash.dynamicweatherapp.presentation.settings.components.PrivacyPolicyDialog
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

    // 2. MODIFIED: Trigger permissions ONLY when we definitively know it is true
    LaunchedEffect(state.hasAcceptedPrivacyPolicy) {
        if (state.hasAcceptedPrivacyPolicy == true) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            )
        }
    }

    // 3. Standard UI Rendering
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
                EmptyStateView(
                    title = "Data Unavailable",
                    message = state.error ?: "We couldn't retrieve the weather data. Please check your connection.",
                    onRetry = {
                        viewModel.loadWeatherInfo()
                    }
                )
            }

            state.weatherInfo != null -> {
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


                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        // 4. Specifically check if it is definitively false
        if (state.hasAcceptedPrivacyPolicy == false && !state.isLoading) {
            PrivacyPolicyDialog(
                onDismiss = {
                    viewModel.acceptPrivacyPolicy()
                }
            )
        }
    }
}