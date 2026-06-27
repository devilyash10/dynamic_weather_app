package dev.yash.dynamicweatherapp.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.yash.dynamicweatherapp.domain.settings.TemperatureUnit
import dev.yash.dynamicweatherapp.presentation.common.GlassCard
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusDark
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val tempUnit by viewModel.temperatureUnit.collectAsState()
    val isCelsius = tempUnit == TemperatureUnit.CELSIUS

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NimbusDark)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = NimbusTextWhite
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Settings Card
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "PREFERENCES",
                    style = MaterialTheme.typography.titleMedium,
                    color = NimbusTextHint
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Temperature Toggle Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Temperature Unit",
                            style = MaterialTheme.typography.bodyLarge,
                            color = NimbusTextWhite
                        )
                        Text(
                            text = if (isCelsius) "Celsius (°C)" else "Fahrenheit (°F)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NimbusTextHint
                        )
                    }

                    Switch(
                        checked = isCelsius,
                        onCheckedChange = { viewModel.toggleTemperatureUnit(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NimbusTextWhite,
                            checkedTrackColor = NimbusAccentBlue,
                            uncheckedThumbColor = NimbusTextHint,
                            uncheckedTrackColor = NimbusDark
                        )
                    )
                }
            }
        }
    }
}