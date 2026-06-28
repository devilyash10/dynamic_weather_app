package dev.yash.dynamicweatherapp.data.worker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.yash.dynamicweatherapp.R
import dev.yash.dynamicweatherapp.data.local.dao.LocationDao
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.firstOrNull

@HiltWorker
class WeatherSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val locationDao: LocationDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val savedLocations = locationDao.getSavedLocations().firstOrNull() ?: emptyList()

            if (savedLocations.isEmpty()) return Result.success()

            savedLocations.forEach { location ->
                val result = weatherRepository.getWeatherData(location.latitude, location.longitude)

                result.fold(
                    onSuccess = { weatherInfo ->
                        // WMO Codes for Heavy Rain, Violent Showers, and Thunderstorms
                        // Bulletproof severe weather list handling both API formats:
                        val severeCodes = listOf("65", "82", "95", "96", "99", "11d", "11n", "09d", "09n")

                        // NOTE: If you are using Int for weather codes, remove the quotes!
                        if (severeCodes.contains(weatherInfo.current.iconId.toString())) {
                            showSevereWeatherNotification(location.name, weatherInfo.current.condition)
                        }
                    },
                    onFailure = { error ->
                        Log.e("WeatherSyncWorker", "Failed to sync ${location.name}: ${error.message}")
                    }
                )
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showSevereWeatherNotification(cityName: String, condition: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, "WEATHER_ALERTS")
            .setSmallIcon(R.drawable.ic_thunderstorm) // Using the SVG we imported earlier!
            .setContentTitle("Severe Weather Alert: $cityName")
            .setContentText("Current condition: $condition. Stay safe!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Using cityName.hashCode() ensures separate cities get separate notifications
        notificationManager.notify(cityName.hashCode(), notification)
    }
}