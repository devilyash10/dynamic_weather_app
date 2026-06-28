package dev.yash.dynamicweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.dynamicweatherapp.presentation.common.CustomBottomNavBar
import dev.yash.dynamicweatherapp.presentation.home.HomeScreen
import dev.yash.dynamicweatherapp.presentation.navigation.Screen
import dev.yash.dynamicweatherapp.presentation.theme.DynamicWeatherAppTheme
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DynamicWeatherAppTheme {
                // navController is the engine that drives the screen swapping
                val navController = rememberNavController()

                // Scaffold provides the structural slot for our Bottom Navigation Bar
                Scaffold(
                    bottomBar = {
                        CustomBottomNavBar(navController = navController)
                    }
                ) { paddingValues ->

                    // NavHost holds the actual screens and handles the routing
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(paddingValues) // Prevents the UI from drawing under the bottom bar
                    ) {

                        // 1. Home Screen Destination
                        composable(
                            route = Screen.Home.routeWithArgs, // Uses the argument pattern
                            arguments = listOf(
                                navArgument("lat") { type = NavType.StringType; nullable = true },
                                navArgument("lon") { type = NavType.StringType; nullable = true },
                                navArgument("cityName") { type = NavType.StringType; nullable = true }
                            )
                        ) {
                            HomeScreen()
                        }

                        // 2. Search Screen Destination
                        composable(Screen.Search.route) {
                            dev.yash.dynamicweatherapp.presentation.search.SearchScreen(navController = navController)
                        }

                        // 3. Settings Screen
                        composable(Screen.Settings.route) {
                            dev.yash.dynamicweatherapp.presentation.settings.SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}