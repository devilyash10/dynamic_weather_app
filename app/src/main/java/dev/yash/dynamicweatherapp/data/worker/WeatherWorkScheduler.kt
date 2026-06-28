package dev.yash.dynamicweatherapp.data.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WeatherWorkScheduler {
    private const val SYNC_WORK_NAME = "weather_background_sync"

    fun scheduleWeatherSync(context: Context, intervalMinutes: Long) {
        val workManager = WorkManager.getInstance(context)

        // If the user selected "Never" (0), cancel all background jobs and exit
        if (intervalMinutes == 0L) {
            workManager.cancelUniqueWork(SYNC_WORK_NAME)
            return
        }

        // Only run if the device has an active internet connection
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true) // Respects battery limits
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<WeatherSyncWorker>(
            intervalMinutes, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        // Enqueue the work. "UPDATE" ensures that if the user changes from 15m to 6h,
        // it updates the existing timer instead of creating two overlapping timers.
        workManager.enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            syncRequest
        )
    }
}