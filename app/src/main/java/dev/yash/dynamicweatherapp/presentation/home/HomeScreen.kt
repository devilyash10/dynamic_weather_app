package dev.yash.dynamicweatherapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import dev.yash.dynamicweatherapp.presentation.theme.NimbusDark
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite

@Composable
fun HomeScreen(
    // Hilt handles injecting the ViewModel for us!
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Observe the state flow from the ViewModel
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NimbusDark)
    ) {
        when {
            // 1. Loading State
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = NimbusTextWhite
                )
            }
            // 2. Error State
            state.error != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.error!!, color = NimbusTextWhite)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadWeatherInfo() }) {
                        Text("Retry")
                    }
                }
            }
            // 3. Success State
            state.weatherInfo != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LocationHeader(locationName = state.locationName)

                    Spacer(modifier = Modifier.height(48.dp))

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
    }
}