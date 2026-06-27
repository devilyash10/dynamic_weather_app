package dev.yash.dynamicweatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_locations")
data class SavedLocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val country: String,
    val admin1: String?, // State or Province
    val latitude: Double,
    val longitude: Double,
    val isDefault: Boolean = false // Future proofing in case they want a manual default location
)