package dev.yash.dynamicweatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.yash.dynamicweatherapp.data.local.entity.SavedLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    // Insert or update a city
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: SavedLocationEntity)

    // Delete a city
    @Delete
    suspend fun deleteLocation(location: SavedLocationEntity)

    // Get all saved cities as a continuous data stream (Flow)
    @Query("SELECT * FROM saved_locations")
    fun getSavedLocations(): Flow<List<SavedLocationEntity>>

    // Check if a city is already saved to prevent duplicates
    @Query("SELECT * FROM saved_locations WHERE latitude = :lat AND longitude = :lon LIMIT 1")
    suspend fun getLocationByCoordinates(lat: Double, lon: Double): SavedLocationEntity?
}