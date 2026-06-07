package com.horizonloop.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.Mid60
import com.horizonloop.app.ui.theme.Muted

@Composable
fun PopupShell(
    title: String,
    onClose: () -> Unit,
    content: @Composable () -> Unit,
    actions: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Mid60)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.86f)
                .clip(RoundedCornerShape(16.dp))
                .background(com.horizonloop.app.ui.theme.Surface)
                .border(1.dp, Muted, RoundedCornerShape(16.dp))
                .padding(20.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = title,
                    color = com.horizonloop.app.ui.theme.Dark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.3).sp,
                    modifier = Modifier.align(Alignment.Center),
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onClose)
                        .padding(4.dp),
                ) {
                    Text(
                        text = "×",
                        color = Mid,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
            content()
            actions()
        }
    }
}
