package dev.yash.dynamicweatherapp.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    val temperatureUnit by viewModel.temperatureUnit.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Local state for the new UI elements (until you wire them to DataStore)
    var syncInterval by remember { mutableStateOf("15") }
    var dynamicBackgrounds by remember { mutableStateOf(true) }
    var weatherNotifications by remember { mutableStateOf(true) }
    var hourlyWidget by remember { mutableStateOf(false) }
    var showDeveloperDialog by remember { mutableStateOf(false) }

    val showUnavailableToast = {
        Toast.makeText(context, "Service currently unavailable", Toast.LENGTH_SHORT).show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NimbusDark)
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = NimbusTextWhite
            )
            Text(
                text = "Customize your experience",
                style = MaterialTheme.typography.bodyMedium,
                color = NimbusAccentBlue
            )

            Spacer(modifier = Modifier.height(28.dp))

            // SECTION 1: PREFERENCES (Temperature)
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Thermostat, contentDescription = null, tint = NimbusTextHint, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Temperature Unit", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(if (temperatureUnit == TemperatureUnit.CELSIUS) "Celsius (°C)" else "Fahrenheit (°F)", style = MaterialTheme.typography.bodyMedium, color = NimbusTextHint)
                    }

                    Switch(
                        checked = temperatureUnit == TemperatureUnit.FAHRENHEIT,
                        onCheckedChange = { viewModel.toggleTemperatureUnit() },
                        colors = SwitchDefaults.colors(checkedTrackColor = NimbusAccentBlue, uncheckedTrackColor = Color.White.copy(alpha = 0.1f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION 2: BACKGROUND SYNC (Missing from previous version)
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = NimbusTextHint, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Background Sync", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SyncOptionRow(title = "Every 15 minutes", selected = syncInterval == "15") { syncInterval = "15" }
                    SyncOptionRow(title = "Every 30 minutes", selected = syncInterval == "30") { syncInterval = "30" }
                    SyncOptionRow(title = "Every hour", selected = syncInterval == "60") { syncInterval = "60" }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION 3: DISPLAY (Missing from previous version)
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LightMode, contentDescription = null, tint = NimbusAccentBlue, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Display", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
                    }

                    DisplayToggleRow(title = "Dynamic Backgrounds", subtitle = "Animated gradients based on weather", checked = dynamicBackgrounds) { dynamicBackgrounds = it }
                    DisplayToggleRow(title = "Weather Notifications", subtitle = "Severe weather alerts", checked = weatherNotifications) { weatherNotifications = it }
                    DisplayToggleRow(title = "Hourly Forecast Widget", subtitle = "Show on lock screen", checked = hourlyWidget) { hourlyWidget = it }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION 4: ABOUT & DEVELOPER
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = NimbusAccentBlue, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("About", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
                    }

                    SettingsInfoRow(title = "Version", value = "2.4.1")
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(horizontal = 20.dp))

                    SettingsInfoRow(title = "Data Source", value = "Open-Meteo API")
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(horizontal = 20.dp))

                    SettingsActionRow(title = "Rate your experience", onClick = showUnavailableToast)
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(horizontal = 20.dp))

                    SettingsActionRow(title = "Privacy Policy", onClick = showUnavailableToast)
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(horizontal = 20.dp))

                    SettingsActionRow(title = "About the Developer", onClick = { showDeveloperDialog = true })
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer branding
            Text(
                text = "Weather data powered by Open-Meteo API\n© 2026 Dynamic Weather",
                style = MaterialTheme.typography.labelMedium,
                color = NimbusTextHint.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(120.dp))
        }

        // --- MODERN GLASSMORPHIC DIALOGUE OVERLAY ---
        if (showDeveloperDialog) {
            Dialog(onDismissRequest = { showDeveloperDialog = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)).padding(horizontal = 32.dp), contentAlignment = Alignment.Center) {
                    GlassCard(modifier = Modifier.fillMaxWidth().wrapContentHeight(), cornerRadius = 28) {
                        Column(modifier = Modifier.fillMaxWidth().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(modifier = Modifier.size(80.dp).background(NimbusAccentBlue.copy(alpha = 0.15f), CircleShape), contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = "Developer", tint = NimbusAccentBlue, modifier = Modifier.size(38.dp))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Yash", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
                            Text("Android Engineer", style = MaterialTheme.typography.labelLarge, color = NimbusAccentBlue)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Passionate developer specialized in clean architectures, fluid Jetpack Compose layouts, and building performant mobile experiences.",
                                style = MaterialTheme.typography.bodyMedium, color = NimbusTextHint, textAlign = TextAlign.Center, lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                            Button(
                                onClick = { showDeveloperDialog = false },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = NimbusAccentBlue, contentColor = Color.White),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Close", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- Reusable Sub-components ---

@Composable
fun SyncOptionRow(title: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = NimbusAccentBlue, unselectedColor = NimbusTextHint)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = NimbusTextWhite)
    }
}

@Composable
fun DisplayToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = NimbusTextHint)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedTrackColor = NimbusAccentBlue, uncheckedTrackColor = Color.White.copy(alpha = 0.1f))
        )
    }
}

@Composable
fun SettingsInfoRow(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = NimbusTextHint)
    }
}

@Composable
fun SettingsActionRow(title: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NimbusTextWhite)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = NimbusTextHint, modifier = Modifier.size(20.dp))
    }
}