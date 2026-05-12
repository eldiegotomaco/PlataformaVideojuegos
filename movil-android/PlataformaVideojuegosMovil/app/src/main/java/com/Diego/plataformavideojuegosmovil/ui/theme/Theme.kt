package com.Diego.plataformavideojuegosmovil.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NeonColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonPink,
    tertiary = NeonPurple,
    background = NeonBackground,
    surface = NeonSurface,
    onPrimary = NeonBackground,
    onSecondary = NeonTextPrimary,
    onTertiary = NeonTextPrimary,
    onBackground = NeonTextPrimary,
    onSurface = NeonTextPrimary,
    error = NeonError
)

@Composable
fun PlataformaVideojuegosMovilTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NeonColorScheme,
        typography = AppTypography,
        content = content
    )
}