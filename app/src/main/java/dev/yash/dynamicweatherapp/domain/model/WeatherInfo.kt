package dev.yash.dynamicweatherapp.domain.model

// The complete package that the UI will observe
data class WeatherInfo(
    val current: CurrentWeather,
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>
)

// Maps directly to the large top section of your Home UI
data class CurrentWeather(
    val temperature: Double,
    val feelsLike: Double,
    val condition: String,
    val iconId: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val uvIndex: Double,
    val sunriseTime: Long,
    val sunsetTime: Long
)

// Maps to the horizontal scrolling row
data class HourlyForecast(
    val time: Long,
    val temperature: Double,
    val iconId: String
)

// Maps to the 5-Day Forecast list
data class DailyForecast(
    val time: Long,
    val minTemp: Double,
    val maxTemp: Double,
    val iconId: String
)