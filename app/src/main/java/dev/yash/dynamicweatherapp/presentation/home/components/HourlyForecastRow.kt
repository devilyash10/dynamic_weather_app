package dev.yash.dynamicweatherapp.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.domain.model.HourlyForecast
import dev.yash.dynamicweatherapp.presentation.common.GlassCard
import dev.yash.dynamicweatherapp.presentation.common.getWeatherIconResource
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@Composable
fun HourlyForecastRow(
    hourlyForecasts: List<HourlyForecast>,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(hourlyForecasts.take(24)) { forecast -> // Limit to next 24 hours
                HourlyForecastItem(forecast = forecast)
            }
        }
    }
}

@Composable
private fun HourlyForecastItem(forecast: HourlyForecast) {
    // Convert Unix timestamp to "2 PM" format
    val date = Date(forecast.time * 1000L)
    val timeFormatted = SimpleDateFormat("h a", java.util.Locale.getDefault()).format(date)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeFormatted,
            style = MaterialTheme.typography.bodyMedium,
            color = NimbusTextHint
        )

        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            painter = painterResource(id = getWeatherIconResource(forecast.iconId)),
            contentDescription = "Weather Icon",
            tint = NimbusTextWhite,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "${forecast.temperature.roundToInt()}°",
            style = MaterialTheme.typography.bodyLarge,
            color = NimbusTextWhite
        )
    }
}