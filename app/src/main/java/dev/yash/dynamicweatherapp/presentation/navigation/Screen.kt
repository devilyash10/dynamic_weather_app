package dev.yash.dynamicweatherapp.presentation.navigation

// A sealed class restricts the types, making navigation type-safe and predictable
sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object Settings : Screen("settings_screen")
}