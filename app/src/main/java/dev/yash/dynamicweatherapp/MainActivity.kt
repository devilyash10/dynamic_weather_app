package dev.yash.dynamicweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

                        // 1. Home Screen (Completed in Phase 5)
                        composable(Screen.Home.route) {
                            HomeScreen()
                        }

                        // 2. Search Screen (Placeholder for Phase 6)
                        composable(Screen.Search.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Search Screen Coming Soon", color = NimbusTextWhite)
                            }
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