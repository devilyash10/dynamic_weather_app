//package dev.yash.dynamicweatherapp.data.remote.dto
//
//import com.google.gson.annotations.SerializedName
//
//// Root JSON Object
//data class OneCallResponseDto(
//    val lat: Double,
//    val lon: Double,
//    val timezone: String,
//    val current: CurrentWeatherDto,
//    val hourly: List<HourlyWeatherDto>,
//    val daily: List<DailyWeatherDto>
//)
//
//// Current weather details matching your UI (Temp, Humidity, Wind, UV, Pressure)
//data class CurrentWeatherDto(
//    val dt: Long, // Unix timestamp
//    val sunrise: Long,
//    val sunset: Long,
//    val temp: Double,
//    @SerializedName("feels_like") val feelsLike: Double,
//    val pressure: Int,
//    val humidity: Int,
//    val uvi: Double, // UV Index
//    @SerializedName("wind_speed") val windSpeed: Double,
//    val weather: List<WeatherConditionDto>
//)
//
//// For the horizontal scroll row (Now, 2 PM, 3 PM...)
//data class HourlyWeatherDto(
//    val dt: Long,
//    val temp: Double,
//    val weather: List<WeatherConditionDto>
//)
//
//// For the 5-Day forecast list
//data class DailyWeatherDto(
//    val dt: Long,
//    val temp: DailyTempDto,
//    val weather: List<WeatherConditionDto>
//)
//
//// Min and Max temperatures for the 5-day list
//data class DailyTempDto(
//    val min: Double,
//    val max: Double
//)
//
//// Condition specifics (e.g., "Clear", ID for icons)
//data class WeatherConditionDto(
//    val id: Int,
//    val main: String,
//    val description: String,
//    val icon: String
//)