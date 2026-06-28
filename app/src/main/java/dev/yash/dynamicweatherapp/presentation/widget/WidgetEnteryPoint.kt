package dev.yash.dynamicweatherapp.presentation.widget

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.yash.dynamicweatherapp.data.local.dao.LocationDao
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    // This allows the widget to grab these dependencies
    fun locationDao(): LocationDao
    fun weatherRepository(): WeatherRepository
}