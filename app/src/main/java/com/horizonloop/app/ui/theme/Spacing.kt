package com.horizonloop.app.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class HLSpacing(
    val xs: Dp = 2.dp,
    val sm: Dp = 6.dp,
    val md: Dp = 8.dp,
    val cardGap: Dp = 12.dp,
    val vPad: Dp = 12.dp,
    val hPad: Dp = 15.dp,
    val sectionGap: Dp = 15.dp,
    val lg: Dp = 16.dp,
    val xl: Dp = 20.dp,
    val xxl: Dp = 30.dp,
    val fab: Dp = 44.dp,
    val notch: Dp = 3.dp,
)

val LocalHLSpacing = compositionLocalOf { HLSpacing() }
