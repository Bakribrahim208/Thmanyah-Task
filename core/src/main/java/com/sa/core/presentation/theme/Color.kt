package com.sa.core.presentation.theme

import androidx.compose.ui.graphics.Color

// Base colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val colorDarkBlue = Color(0xFF22222F)

// Transparency colors
val colorTransparent = Color.Transparent
val colorBlackSemiTransparent = Color(0xCC000000)  // 80% opacity black
val colorBlackSemiTransparent50 = Color(0x80000000) // 50% opacity black
val colorBlackSemiTransparent25 = Color(0x40000000) // 25% opacity black
val colorBlackSemiTransparent10 = Color(0x1A000000) // 10% opacity black

// Dark mode colors
val DarkPrimary = Color(0xFFAB2D00) // Orange highlight color used on tabs/features
val DarkBackground = Color(0xFF121212) // Very dark background
val DarkSurface = Color(0xFF1E1E1E) // Card background color
val DarkOnSurface = Color(0xFFFFFFFF) // White text on cards
val DarkSecondary = Color(0xFFFFEB3B) // Yellow accent (for the stars)
val DarkTertiary = Color(0xFF010531) // Blue accent (seen in some cards)

// Light mode equivalent colors
val LightPrimary = Color(0xFFFF5722) // Keep the orange as primary
val LightBackground = Color(0xFFF5F5F5) // Light gray background
val LightSurface = Color(0xFFFFFFFF) // White card background
val LightOnSurface = Color(0xFF121212) // Black text on cards
val LightSecondary = Color(0xFFFFC107) // Slightly darker yellow for visibility
val LightTertiary = Color(0xFFC9CDE1) // Slightly darker blue for visibility

// Additional colors
val GrayText = Color(0xFF757575) // Gray text for timestamps/secondary info
val DarkGrayBackground = Color(0xFF242424) // Slightly lighter dark background for cards
val LightGrayBackground = Color(0xFFE0E0E0) // Light gray for card backgrounds in light mode
