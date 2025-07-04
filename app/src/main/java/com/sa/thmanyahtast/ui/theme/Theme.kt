package com.sa.thmanyahtast.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.sa.core.presentation.theme.Typography
// Dark mode colors (as seen in the screenshot)
val DarkPrimary = Color(0xFFFF5722) // Orange highlight color used on tabs/features
val DarkBackground = Color(0xFF121212) // Very dark background
val DarkSurface = Color(0xFF1E1E1E) // Card background color
val DarkOnSurface = Color(0xFFFFFFFF) // White text on cards
val DarkSecondary = Color(0xFFFFEB3B) // Yellow accent (for the stars)
val DarkTertiary = Color(0xFF010531) // Blue accent (seen in some cards)

// Light mode equivalent colors (inverted appropriately)
val LightPrimary = Color(0xFFFF5722) // Keep the orange as primary
val LightBackground = Color(0xFFF5F5F5) // Light gray background
val LightSurface = Color(0xFFFFFFFF) // White card background
val LightOnSurface = Color(0xFF121212) // Black text on cards
val LightSecondary = Color(0xFFFFC107) // Slightly darker yellow for visibility
val LightTertiary = Color(0xFFC9CDE1) // Slightly darker blue for visibility

// Additional colors from the UI
val GrayText = Color(0xFF757575) // Gray text for timestamps/secondary info
val DarkGrayBackground = Color(0xFF242424) // Slightly lighter dark background for cards
val LightGrayBackground = Color(0xFFE0E0E0) // Light gray for card backgrounds in light mode

// Creating the color schemes
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.White,
    secondary = DarkSecondary,
    onSecondary = Color.Black,
    tertiary = DarkTertiary,
    onTertiary = Color.White,
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkGrayBackground,
    onSurfaceVariant = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    secondary = LightSecondary,
    onSecondary = Color.Black,
    tertiary = LightTertiary,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = Color.Black,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightGrayBackground,
    onSurfaceVariant = Color.Black
)

@Composable
fun ThmanyahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Using typography from core module
        content = content
    )
}