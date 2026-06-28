package dev.yash.dynamicweatherapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextHint
import dev.yash.dynamicweatherapp.presentation.theme.NimbusTextWhite

@Composable
fun LocationHeader(
    locationName: String,
    lastUpdated: String, // NEW: Accepts the parameter
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Side: Location Icon and City Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f) // Allows text to truncate if too long
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Current Location",
                tint = NimbusTextWhite,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = locationName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = NimbusTextWhite,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Right Side: "Just updated" Badge (Matching your mockup exactly)
        Box(
            modifier = Modifier
                .background(
                    color = Color.White.copy(alpha = 0.1f), // Glassmorphic tint
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lastUpdated,
                style = MaterialTheme.typography.labelMedium,
                color = NimbusTextHint
            )
        }
    }
}