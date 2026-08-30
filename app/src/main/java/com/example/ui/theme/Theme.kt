package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryPink,
    onPrimary = DarkButtonText,
    primaryContainer = DarkSoftPink,
    onPrimaryContainer = DarkPrimaryPink,
    secondary = AccentPink,
    onSecondary = DarkButtonText,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = DarkTextWhite,
    tertiary = DeepPink,
    onTertiary = DarkTextWhite,
    background = DarkBackground,
    onBackground = DarkTextWhite,
    surface = DarkCard,
    onSurface = DarkTextWhite,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = DarkSecondaryText,
    outline = DarkBorder,
    outlineVariant = DarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPink,
    onPrimary = PureBlack,
    primaryContainer = SoftPink,
    onPrimaryContainer = DeepPink,
    secondary = DeepPink,
    onSecondary = PureWhite,
    secondaryContainer = SoftPink,
    onSecondaryContainer = PureBlack,
    tertiary = AccentPink,
    onTertiary = PureWhite,
    background = PureWhite,
    onBackground = PureBlack,
    surface = CardBackgroundLight,
    onSurface = PureBlack,
    surfaceVariant = SoftPink,
    onSurfaceVariant = MutedGray,
    outline = BorderLight,
    outlineVariant = BorderLight
)

@Composable
fun SaaSAppTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
