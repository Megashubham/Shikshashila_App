package com.shikshashila.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Always light — matches the website which has no dark mode
private val LightColorScheme = lightColorScheme(
    primary              = AccentPurple,        // #8b5cf6
    onPrimary            = TextWhite,
    primaryContainer     = Color(0xFFEDE9FE),
    onPrimaryContainer   = Color(0xFF3B0764),

    secondary            = ButtonDark,          // #374151 — matches btn-secondary
    onSecondary          = TextWhite,
    secondaryContainer   = Color(0xFFE5E7EB),
    onSecondaryContainer = TextPrimary,

    tertiary             = AccentPink,          // #ec4899
    onTertiary           = TextWhite,

    background           = BackgroundLight,     // #dfe9f3
    onBackground         = TextPrimary,

    surface              = SurfaceWhite,        // card white
    onSurface            = TextPrimary,
    surfaceVariant       = Color(0xFFF1F2F7),
    onSurfaceVariant     = TextMuted,

    outline              = CardBorder,          // #E5E7EB

    error                = ErrorRed,
    onError              = TextWhite,
)

@Composable
fun ShikshashilaAppTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // White status bar with dark icons — matches the website's light background
            window.statusBarColor = SurfaceWhite.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography,
        content     = content
    )
}
