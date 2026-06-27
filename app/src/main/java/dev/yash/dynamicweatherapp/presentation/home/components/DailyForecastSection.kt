package dev.yash.dynamicweatherapp.presentation.home.components

import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit
import dev.yash.dynamicweatherapp.presentation.common.toFormattedTemp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.domain.model.DailyForecast
import dev.yash.dynamicweatherapp.presentation.common.GlassCard
import dev.yash.dynamicweatherapp.presentation.common.getWeatherIconResource
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun DailyForecastSection(
    dailyForecasts: List<DailyForecast>,
    temperatureUnit: TemperatureUnit,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "5-DAY FORECAST",
                style = MaterialTheme.typography.titleMedium,
                color = NimbusTextHint
            )

            Spacer(modifier = Modifier.height(16.dp))

            // We use .take(5) to ensure we only show 5 days, even if the API returns 7 or 8
            dailyForecasts.take(5).forEachIndexed { index, forecast ->
                DailyForecastItem(
                    forecast = forecast,
                    isToday = index == 0,
                    temperatureUnit = temperatureUnit // UPDATED
                )

                // Add spacing between items, but not after the last one
                if (index < 4) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun DailyForecastItem(
    forecast: DailyForecast,
    isToday: Boolean,
    temperatureUnit: TemperatureUnit
) {
    // Format the Unix timestamp to "Mon", "Tue", etc.
    val dayFormat = SimpleDateFormat("EEE", java.util.Locale.getDefault())
    val dayString = if (isToday) "Today" else dayFormat.format(Date(forecast.time * 1000L))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Day Name
        Text(
            text = dayString,
            style = MaterialTheme.typography.bodyLarge,
            color = NimbusTextWhite,
            modifier = Modifier.width(60.dp) // Fixed width to align icons perfectly
        )

        // 2. Weather Icon
        Icon(
            painter = painterResource(id = getWeatherIconResource(forecast.iconId)),
            contentDescription = "Weather",
            tint = NimbusTextWhite,
            modifier = Modifier.size(24.dp)
        )

        // 3. Min/Max Temps with a visual temperature bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.width(140.dp)
        ) {
            Text(
                text = forecast.minTemp.toFormattedTemp(temperatureUnit), // UPDATED
                style = MaterialTheme.typography.bodyLarge,
                color = NimbusTextHint
            )

            Spacer(modifier = Modifier.width(12.dp))

            // The little temperature bar from your UI design
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(NimbusAccentBlue.copy(alpha = 0.3f))
            ) {
                // Inside the bar, you could dynamically calculate width based on min/max later.
                // For now, it's a solid accent bar matching the aesthetic.
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(2.dp))
                    .background(NimbusAccentBlue)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = forecast.maxTemp.toFormattedTemp(temperatureUnit), // UPDATED
                style = MaterialTheme.typography.bodyLarge,
                color = NimbusTextWhite
            )
        }
    }
}