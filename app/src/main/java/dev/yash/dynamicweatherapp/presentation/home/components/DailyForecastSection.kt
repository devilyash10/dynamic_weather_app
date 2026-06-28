package dev.yash.dynamicweatherapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yash.dynamicweatherapp.domain.model.DailyForecast
import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit
import dev.yash.dynamicweatherapp.presentation.common.GlassCard
import dev.yash.dynamicweatherapp.presentation.common.getWeatherIconResource
import dev.yash.dynamicweatherapp.presentation.common.toFormattedTemp
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DailyForecastSection(
    dailyForecasts: List<DailyForecast>,
    temperatureUnit: TemperatureUnit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        // 1. Header is now OUTSIDE the GlassCard
        Text(
            text = "5-DAY FORECAST",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = NimbusTextHint,
            modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
        )

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val top5 = dailyForecasts.take(5)

                top5.forEachIndexed { index, forecast ->
                    DailyForecastItem(
                        forecast = forecast,
                        isToday = index == 0,
                        temperatureUnit = temperatureUnit
                    )

                    // 2. Add subtle dividers between rows (except for the last one)
                    if (index < top5.size - 1) {
                        HorizontalDivider(
                            color = Color.White.copy(alpha = 0.05f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
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
    val date = Date(forecast.time * 1000L)
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dayText = if (isToday) "Today" else dayFormat.format(date)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp), // Increased padding for premium feel
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Day Name
        Text(
            text = dayText,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = NimbusTextWhite,
            modifier = Modifier.width(64.dp)
        )

        // Weather Icon
        Icon(
            painter = painterResource(id = getWeatherIconResource(forecast.iconId)),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(28.dp),
            // We set tint to Unspecified so custom colored SVGs will show their actual colors!
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.weight(1f))

        // Min Temperature (Dimmed)
        Text(
            text = forecast.minTemp.toFormattedTemp(temperatureUnit),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = NimbusTextHint,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.width(16.dp))

        // The Layered Gradient Temperature Bar
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(6.dp)
                .background(
                    color = Color.White.copy(alpha = 0.08f), // Dark background track
                    shape = RoundedCornerShape(3.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            // The colorful gradient range indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Width of the gradient bar
                    .offset(x = 16.dp)  // Shift it slightly right
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5E9BC8), // Soft Blue
                                Color(0xFFECAE5E)  // Warm Yellow/Orange
                            )
                        ),
                        shape = RoundedCornerShape(3.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Max Temperature (Bold)
        Text(
            text = forecast.maxTemp.toFormattedTemp(temperatureUnit),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = NimbusTextWhite,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
    }
}