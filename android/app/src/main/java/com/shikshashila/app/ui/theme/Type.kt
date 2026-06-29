package com.shikshashila.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.shikshashila.app.R

// ─── Manrope via Google Fonts (same font as the website) ──────────────────
private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage   = "com.google.android.gms",
    certificates      = R.array.com_google_android_gms_fonts_certs
)

val ManropeFontFamily = FontFamily(
    Font(GoogleFont("Manrope"), provider, FontWeight.Normal),
    Font(GoogleFont("Manrope"), provider, FontWeight.Medium),
    Font(GoogleFont("Manrope"), provider, FontWeight.SemiBold),
    Font(GoogleFont("Manrope"), provider, FontWeight.Bold),
    Font(GoogleFont("Manrope"), provider, FontWeight.ExtraBold),
)

// ─── Typography scale ─────────────────────────────────────────────────────
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.ExtraBold,
        fontSize     = 57.sp,
        lineHeight   = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.ExtraBold,
        fontSize     = 45.sp,
        lineHeight   = 52.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize   = 40.sp,
        lineHeight = 48.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize   = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize   = 24.sp,
        lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 16.sp,
        lineHeight   = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.Medium,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.Normal,
        fontSize     = 16.sp,
        lineHeight   = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.Normal,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.Normal,
        fontSize     = 12.sp,
        lineHeight   = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.Medium,
        fontSize     = 12.sp,
        lineHeight   = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily   = ManropeFontFamily,
        fontWeight   = FontWeight.Medium,
        fontSize     = 11.sp,
        lineHeight   = 16.sp,
        letterSpacing = 0.5.sp
    ),
)
