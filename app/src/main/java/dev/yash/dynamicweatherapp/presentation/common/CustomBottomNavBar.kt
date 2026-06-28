package dev.yash.dynamicweatherapp.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.yash.dynamicweatherapp.presentation.navigation.Screen
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusDark
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint

@Composable
fun CustomBottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // The dark background from your theme
    NavigationBar(
        containerColor = NimbusDark,
        tonalElevation = 0.dp // Flattens it out so it blends with the background
    ) {
        // 1. Home Tab
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = navigationBarColors()
        )

        // 2. Search/Cities Tab
        NavigationBarItem(
            selected = currentRoute == Screen.Search.route,
            onClick = {
                navController.navigate(Screen.Search.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Search, contentDescription = "Cities") },
            label = { Text("Cities") },
            colors = navigationBarColors()
        )

        // 3. Settings Tab
        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = {
                navController.navigate(Screen.Settings.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            colors = navigationBarColors()
        )
    }
}

// Reusable color styling for the tabs
@Composable
private fun navigationBarColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = NimbusAccentBlue,
    selectedTextColor = NimbusAccentBlue,
    unselectedIconColor = NimbusTextHint,
    unselectedTextColor = NimbusTextHint,
    indicatorColor = Color.Transparent // Removes the pill-shaped background on selected items for a cleaner look
)