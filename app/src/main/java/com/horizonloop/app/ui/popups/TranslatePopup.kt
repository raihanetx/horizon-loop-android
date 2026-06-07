package com.horizonloop.app.ui.popups

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.Mid60

@Composable
fun TranslatePopup(
    visible: Boolean,
    progress: Float,
) {
    if (!visible) return
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(300),
        label = "translate-progress",
    )
    Box(modifier = Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Color(0x99000000))) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = "Processing",
                color = Mid,
                fontSize = 14.sp,
            )
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                color = Mid,
                fontSize = 14.sp,
            )
        }
    }
}
