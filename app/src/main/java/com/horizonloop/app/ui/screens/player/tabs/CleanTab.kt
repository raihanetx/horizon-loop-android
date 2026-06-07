package com.horizonloop.app.ui.screens.player.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.data.model.Dialogue
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.White15
import com.horizonloop.app.ui.util.HLFormat

@Composable
fun CleanTab(
    dialogues: List<Dialogue>,
    currentMs: Long,
    audioMode: Boolean,
    speedPopupText: String,
    speedPopupVisible: Boolean,
    onSpeedChange: (Float) -> Unit,
    onSpeedPopupShow: (String) -> Unit,
    onSpeedPopupHide: () -> Unit,
    baseSpeed: Float = 1f,
    modifier: Modifier = Modifier,
) {
    val current = dialogues.firstOrNull { currentMs in it.startMs..it.endMs }
        ?: dialogues.minByOrNull { kotlin.math.abs((it.startMs + it.endMs) / 2 - currentMs) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(baseSpeed) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val isLeft = down.position.x < size.width / 2f
                    val target = if (isLeft) 0.75f else 1.25f

                    onSpeedChange(target)
                    onSpeedPopupShow("${target}x")

                    try {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Main)
                            if (event.changes.all { !it.pressed }) break
                        }
                    } finally {
                        onSpeedChange(baseSpeed)
                        onSpeedPopupHide()
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (audioMode) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(White15.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.GraphicEq,
                        contentDescription = null,
                        tint = Dark.copy(alpha = 0.4f),
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
            if (current != null && !audioMode) {
                Text(
                    text = "[${HLFormat.time(current.startMs)}] ${current.english}",
                    color = Dark,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = current.bangla,
                    color = Mid,
                    fontSize = 17.sp,
                    lineHeight = 27.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
        AnimatedVisibility(
            visible = speedPopupVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(999.dp))
                    .background(androidx.compose.ui.graphics.Color(0x80000000))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Text(
                    text = speedPopupText,
                    color = Dark,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Text(
            text = "Hold Left half to slow down  •  Hold Right to speed up",
            color = Dark.copy(alpha = 0.2f),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.7.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}
