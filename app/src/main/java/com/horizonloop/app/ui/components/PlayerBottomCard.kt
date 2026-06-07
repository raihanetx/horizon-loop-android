package com.horizonloop.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.Deep
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.Muted
import com.horizonloop.app.ui.theme.Surface
import com.horizonloop.app.ui.theme.White15

@Composable
fun PlayerBottomCard(
    title: String,
    isPlaying: Boolean,
    currentMs: Long,
    totalMs: Long,
    speed: Float,
    mode: String,
    loopCount: Int,
    onRewind: () -> Unit,
    onForward: () -> Unit,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Surface)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 48.dp, height = 4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(White15)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = title,
                color = Dark,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MetaBadge("Mode: $mode")
                MetaDivider()
                MetaBadge("Loop: ${if (loopCount == 0) "None" else loopCount.toString()}")
                MetaDivider()
                MetaBadge("Speed: ${speed}x")
            }
        }
        ProgressBar(
            currentMs = currentMs,
            totalMs = totalMs,
            onSeek = onSeek,
        )
        PlayerControls(
            isPlaying = isPlaying,
            onRewind = onRewind,
            onPlayPause = onPlayPause,
            onForward = onForward,
        )
    }
}

@Composable
private fun MetaBadge(text: String) {
    Text(
        text = text,
        color = Mid,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp,
    )
}

@Composable
private fun MetaDivider() {
    Text(
        text = "|",
        color = Muted,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
    )
}
