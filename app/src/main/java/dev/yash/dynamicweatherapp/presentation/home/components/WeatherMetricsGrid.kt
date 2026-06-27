package dev.yash.dynamicweatherapp.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.domain.model.CurrentWeather
import dev.yash.dynamicweatherapp.presentation.common.GlassCard
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun WeatherMetricsGrid(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    val timeFormatter = SimpleDateFormat("h:mm a", java.util.Locale.getDefault())
    val sunrise = timeFormatter.format(Date(currentWeather.sunriseTime * 1000L))
    val sunset = timeFormatter.format(Date(currentWeather.sunsetTime * 1000L))

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Row 1: Humidity & Wind
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                title = "HUMIDITY",
                value = "${currentWeather.humidity}%",
                subtitle = "Moderate", // This can be calculated dynamically later
                // NOTE: Use a standard Material Icon or import your own SVG here
                icon = androidx.compose.material.icons.Icons.Default.Info,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "WIND",
                value = "${currentWeather.windSpeed} km/h",
                subtitle = "Steady",
                icon = androidx.compose.material.icons.Icons.Default.Share,
                modifier = Modifier.weight(1f)
            )
        }

        // Row 2: Sunrise & Sunset
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                title = "SUNRISE",
                value = sunrise,
                subtitle = "Morning",
                icon = androidx.compose.material.icons.Icons.Default.KeyboardArrowUp,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "SUNSET",
                value = sunset,
                subtitle = "Evening",
                icon = androidx.compose.material.icons.Icons.Default.KeyboardArrowDown,
                modifier = Modifier.weight(1f)
            )
        }

        // Row 3: UV Index & Pressure
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                title = "UV INDEX",
                value = currentWeather.uvIndex.toString(),
                subtitle = "SPF recommended",
                icon = androidx.compose.material.icons.Icons.Default.Warning,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "PRESSURE",
                value = "${currentWeather.pressure} hPa",
                subtitle = "Steady",
                icon = androidx.compose.material.icons.Icons.Default.Build,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = NimbusAccentBlue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = NimbusTextWhite
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = NimbusTextHint
            )
        }
    }
}