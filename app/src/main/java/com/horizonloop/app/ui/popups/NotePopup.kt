package com.horizonloop.app.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.components.HLPillButton
import com.horizonloop.app.ui.components.PopupShell
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.Mid

@Composable
fun NotePopup(
    visible: Boolean,
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
) {
    if (!visible) return
    PopupShell(
        title = title,
        onClose = onClose,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .padding(vertical = 2.dp),
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textStyle = LocalTextStyle.current.merge(
                        TextStyle(
                            color = Dark,
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    ),
                    cursorBrush = SolidColor(Dark),
                    decorationBox = { inner ->
                        if (text.isEmpty()) {
                            Text(
                                text = "Type your note...",
                                color = Mid,
                                fontSize = 14.sp,
                            )
                        }
                        inner()
                    }
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                HLPillButton("Save", onSave, modifier = Modifier.weight(1f))
                HLPillButton("Cancel", onClose, modifier = Modifier.weight(1f))
            }
        }
    )
}
