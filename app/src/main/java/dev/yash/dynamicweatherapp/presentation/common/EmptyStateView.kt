package dev.yash.dynamicweatherapp.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.presentation.theme.NimbusAccentBlue
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite

@Composable
fun EmptyStateView(
    title: String = "No Connection",
    message: String = "Please check your internet connection and try again.",
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // A subtle, glowing icon container
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = NimbusAccentBlue.copy(alpha = 0.1f),
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.WifiOff,
                    contentDescription = "No Internet",
                    tint = NimbusAccentBlue,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = NimbusTextWhite
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = NimbusTextHint,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Premium filled button for the retry action
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NimbusAccentBlue,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Try Again",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}