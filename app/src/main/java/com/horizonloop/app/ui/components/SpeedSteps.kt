package com.horizonloop.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.White8
import com.horizonloop.app.ui.theme.White15

@Composable
fun SpeedSteps(
    steps: List<Float>,
    current: Float,
    onSelect: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
    ) {
        steps.forEachIndexed { index, value ->
            val active = value == current
            val dotScale by animateFloatAsState(
                targetValue = if (active) 1.3f else 1f,
                label = "speed-dot-scale"
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .scale(dotScale)
                        .background(
                            color = if (active) Dark else Color(0x1AFFFFFF),
                            shape = CircleShape
                        )
                        .clickable { onSelect(value) }
                )
                Text(
                    text = "${value}x",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (active) Dark else Color(0x4DFFFFFF),
                )
            }
        }
    }
}
