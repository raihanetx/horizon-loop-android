package com.horizonloop.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val HLColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = Deep,
    secondary = Mid,
    onSecondary = Deep,
    background = Deep,
    onBackground = Dark,
    surface = Surface,
    onSurface = Dark,
    surfaceVariant = Muted,
    onSurfaceVariant = Mid,
    outline = Muted,
    outlineVariant = Mid,
)

@Composable
fun HorizonLoopTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalHLSpacing provides HLSpacing()) {
        MaterialTheme(
            colorScheme = HLColorScheme,
            typography = HLTypography,
            shapes = HLShapes,
            content = content,
        )
    }
}

object HL {
    val spacing: HLSpacing
        @Composable get() = LocalHLSpacing.current
}
