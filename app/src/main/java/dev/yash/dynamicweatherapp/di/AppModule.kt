package dev.yash.dynamicweatherapp.di

import androidx.room.Room
import dev.yash.dynamicweatherapp.data.local.WeatherDatabase
import dev.yash.dynamicweatherapp.data.local.dao.LocationDao
import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.yash.dynamicweatherapp.data.location.DefaultLocationTracker
import dev.yash.dynamicweatherapp.data.remote.OpenMeteoApi
import dev.yash.dynamicweatherapp.data.repository.WeatherRepositoryImpl
import dev.yash.dynamicweatherapp.domain.location.LocationTracker
import dev.yash.dynamicweatherapp.domain.repository.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenMeteoApi {
        return Retrofit.Builder()
            // CHANGE THIS LINE:
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        locationClient: FusedLocationProviderClient,
        app: Application
    ): LocationTracker {
        return DefaultLocationTracker(locationClient, app)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenMeteoApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    // Add these inside your AppModule class:
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: android.content.Context): androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> {
        return androidx.datastore.preferences.core.PreferenceDataStoreFactory.create(
            // Notice we just call it directly on context now:
            produceFile = { context.preferencesDataStoreFile("settings_prefs") }
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>): dev.yash.dynamicweatherapp.domain.settings.SettingsRepository {
        return dev.yash.dynamicweatherapp.data.repository.SettingsRepositoryImpl(dataStore)
    }
    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: android.content.Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationDao(db: WeatherDatabase): LocationDao {
        return db.locationDao
    }
}