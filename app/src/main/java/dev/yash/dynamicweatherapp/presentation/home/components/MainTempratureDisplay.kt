package dev.yash.dynamicweatherapp.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.domain.model.CurrentWeather
import dev.yash.dynamicweatherapp.presentation.common.getWeatherIconResource
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import kotlin.math.roundToInt

@Composable
fun MainTemperatureDisplay(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Standard Compose Icon handling. If you haven't added the SVG icons yet,
        // this will just render the fallback Android logo we set up earlier.
        androidx.compose.material3.Icon(
            painter = painterResource(id = getWeatherIconResource(currentWeather.iconId)),
            contentDescription = currentWeather.condition,
            tint = NimbusAccentBlue,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Large Temperature (e.g., "18°C")
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "${currentWeather.temperature.roundToInt()}°",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "C", // We will make this dynamic in Settings Phase
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = currentWeather.condition,
            style = MaterialTheme.typography.headlineMedium,
            color = NimbusAccentBlue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Feels like ${currentWeather.feelsLike.roundToInt()}°C",
            style = MaterialTheme.typography.bodyLarge,
            color = NimbusTextHint
        )
    }
}