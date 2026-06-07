package com.horizonloop.app.ui.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.horizonloop.app.ui.theme.Deep
import com.horizonloop.app.ui.theme.FrameOutside

@Composable
fun PhoneContainer(content: @Composable () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FrameOutside),
        contentAlignment = Alignment.Center
    ) {
        val isCompact = maxWidth < 600.dp
        val frameColor = Deep

        Box(
            modifier = Modifier
                .then(
                    if (isCompact) Modifier.fillMaxSize()
                    else Modifier
                        .widthIn(max = 440.dp)
                        .heightIn(max = 840.dp)
                        .fillMaxHeight()
                        .padding(24.dp)
                )
                .clip(RoundedCornerShape(if (isCompact) 0.dp else 16.dp))
                .background(frameColor)
        ) {
            content()
        }
    }
}
