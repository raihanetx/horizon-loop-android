package com.horizonloop.app.ui.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

val NavUpEasing = CubicBezierEasing(0.22f, 1f, 0.36f, 1f)
val PopupEasing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)

fun slideUp(durationMs: Int = 200) = slideInVertically(
    animationSpec = tween(durationMs, easing = PopupEasing),
    initialOffsetY = { it / 5 }
) + fadeIn(animationSpec = tween(durationMs))

fun slideDown(durationMs: Int = 250) = slideInVertically(
    animationSpec = tween(durationMs, easing = PopupEasing),
    initialOffsetY = { -it / 5 }
) + fadeIn(animationSpec = tween(durationMs))

fun slideUpOut(durationMs: Int = 200) = slideOutVertically(
    animationSpec = tween(durationMs, easing = PopupEasing),
    targetOffsetY = { it / 5 }
) + fadeOut(animationSpec = tween(durationMs))

fun slideDownOut(durationMs: Int = 250) = slideOutVertically(
    animationSpec = tween(durationMs, easing = PopupEasing),
    targetOffsetY = { -it / 5 }
) + fadeOut(animationSpec = tween(durationMs))
