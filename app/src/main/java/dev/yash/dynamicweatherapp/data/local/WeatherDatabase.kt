package dev.yash.dynamicweatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.yash.dynamicweatherapp.data.local.dao.LocationDao
import dev.yash.dynamicweatherapp.data.local.entity.SavedLocationEntity

@Database(
    entities = [SavedLocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
}