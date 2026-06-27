package dev.yash.dynamicweatherapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// We map the specific text styles from your UI mockups to Material 3 standard slots
val Typography = Typography(
    // Used for the massive temperature (e.g., "18°")
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 72.sp,
        lineHeight = 80.sp,
        color = NimbusTextWhite
    ),
    // Used for City Names ("San Francisco")
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = NimbusTextWhite
    ),
    // Used for Section Titles ("HUMIDITY", "WIND")
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 1.sp, // Gives that slight uppercase spread
        color = NimbusTextHint
    ),
    // Used for standard text and list items
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = NimbusTextWhite
    ),
    // Used for secondary text ("Feels like 16°C")
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = NimbusTextHint
    )
)