package dev.yash.dynamicweatherapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home") {
        const val routeWithArgs = "home?lat={lat}&lon={lon}&cityName={cityName}"

        fun passCoordinates(lat: Double, lon: Double, cityName: String): String {
            return "home?lat=$lat&lon=$lon&cityName=$cityName"
        }
    }
    object Search : Screen("search")
    object Settings : Screen("settings")
}