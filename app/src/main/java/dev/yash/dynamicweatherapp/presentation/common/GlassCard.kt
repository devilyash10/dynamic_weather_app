package dev.yash.dynamicweatherapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.yash.dynamicweatherapp.presentation.theme.NimbusGlass
import dev.yash.dynamicweatherapp.presentation.theme.NimbusGlassBorder

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp, // Default rounding from your UI
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(NimbusGlass)
            .border(
                width = 1.dp,
                color = NimbusGlassBorder,
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        // The child views (text, icons, etc.) will be placed inside this Box
        content()
    }
}