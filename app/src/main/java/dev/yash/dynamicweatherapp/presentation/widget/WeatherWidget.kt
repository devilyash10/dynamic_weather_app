package dev.yash.dynamicweatherapp.presentation.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.*
import androidx.glance.text.*
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.firstOrNull

class WeatherWidget : GlanceAppWidget() {

    // provideGlance runs in the background, making it safe to fetch data!
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // 1. Grab our database and repository using the Hilt Entry Point
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        )
        val locationDao = entryPoint.locationDao()
        val repository = entryPoint.weatherRepository()

        // 2. Setup default fallback data
        var cityName = "No City Saved"
        var temperature = "--"
        var condition = "Open app to add a city"

        // 3. Fetch the first saved city from the database
        val savedLocations = locationDao.getSavedLocations().firstOrNull() ?: emptyList()
        val targetLocation = savedLocations.firstOrNull()

        // 4. If we have a city, fetch the live weather for it
        if (targetLocation != null) {
            cityName = targetLocation.name
            repository.getWeatherData(targetLocation.latitude, targetLocation.longitude)
                .onSuccess { weather ->
                    temperature = "${weather.current.temperature.toInt()}°"
                    condition = weather.current.condition
                }
        }

        // 5. Draw the actual widget UI
        provideContent {
            WidgetLayout(cityName, temperature, condition)
        }
    }
}

@Composable
private fun WidgetLayout(cityName: String, temp: String, desc: String) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            // A semi-transparent dark background for that modern glass look
            .background(ColorProvider(day = Color(0xFF121212).copy(alpha = 0.8f), night = Color(0xFF121212).copy(alpha = 0.8f)))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = temp,
                style = TextStyle(
                    // THE FIX: Explicitly setting day and night colors
                    color = ColorProvider(day = Color.White, night = Color.White),
                    fontSize = 42.sp, // Massive temperature text
                    fontWeight = FontWeight.Bold
                )
            )
            Column(
                modifier = GlanceModifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = cityName,
                    style = TextStyle(
                        // THE FIX: Explicitly setting day and night colors
                        color = ColorProvider(day = Color.White, night = Color.White),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
    //            Spacer(modifier = GlanceModifier.height(4.dp))
//                Text(
//                    text = temp,
//                    style = TextStyle(
//                        // THE FIX: Explicitly setting day and night colors
//                        color = ColorProvider(day = Color.White, night = Color.White),
//                        fontSize = 28.sp, // Massive temperature text
//                        fontWeight = FontWeight.Bold
//                    )
//                )
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(
                    text = desc,
                    style = TextStyle(
                        // THE FIX: Explicitly setting day and night colors
                        color = ColorProvider(day = Color(0xFF90CAF9), night = Color(0xFF90CAF9)),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}
// The Broadcast Receiver
class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()
}