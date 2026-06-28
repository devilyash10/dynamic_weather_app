package dev.yash.dynamicweatherapp.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue

@Composable
fun PrivacyPolicyDialog(
    onDismiss: () -> Unit
) {
    // We use DialogProperties to allow the dialog to expand closer to the screen edges
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E),
                contentColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f) // Takes up 85% of the screen height for easy scrolling
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // FIXED HEADER
                Text(
                    text = "Privacy Policy",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(16.dp))

                // SCROLLABLE CONTENT BODY
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .weight(1f) // This forces the column to take up the middle space
                        .verticalScroll(scrollState)
                ) {
                    PolicySection(
                        title = "Effective Date",
                        body = "June 29, 2026"
                    )

                    PolicySection(
                        title = "1. Introduction",
                        body = "Yash Bhadoriya built the AtmosLive Weather app as a free/freemium application. This service is provided at no cost and is intended for use as is. This page informs users regarding my policies regarding the collection, use, and disclosure of Personal Information."
                    )

                    PolicySection(
                        title = "2. Information Collection and Use",
                        body = "AtmosLive Weather is designed with privacy in mind. The app relies on local device storage and minimizes data collection.\n\n• Location Data: To provide real-time weather forecasts, the app requests permission to access your device's location. This data is used only to fetch weather conditions. It is not tracked, sold, or stored on external servers.\n\n• Local Storage: Any cities you search for and save are stored securely and locally on your device's internal database. Your settings are also saved locally."
                    )

                    PolicySection(
                        title = "3. Third-Party Services",
                        body = "AtmosLive Weather uses the Open-Meteo API to retrieve accurate weather forecasts. When the app fetches the weather, it sends geographic coordinates (latitude and longitude) to Open-Meteo's servers solely to return the correct forecast data. Open-Meteo's handling of this data is governed by their own privacy policy."
                    )

                    PolicySection(
                        title = "4. Data Security",
                        body = "I value your trust in using this app. Because your saved cities and preferences are stored locally on your device, they are protected by Android's native security sandboxing. However, remember that no method of transmission over the internet, or method of electronic storage, is 100% secure."
                    )

                    PolicySection(
                        title = "5. Contact",
                        body = "If you have any questions or suggestions about this Privacy Policy, please contact the developer via the official repository or email."
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(16.dp))

                // FIXED FOOTER (Close Button)
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NimbusAccentBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Accept & Close",
                        modifier = Modifier.padding(vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

// Helper Composable to keep the formatting clean and consistent
@Composable
private fun PolicySection(title: String, body: String) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = NimbusAccentBlue
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray,
            lineHeight = 22.sp // Adds a bit of breathing room between lines of text
        )
    }
}