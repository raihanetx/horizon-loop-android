package com.horizonloop.app.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.components.HLFieldSet
import com.horizonloop.app.ui.components.HLPillButton
import com.horizonloop.app.ui.components.PopupShell
import com.horizonloop.app.ui.theme.Mid

@Composable
fun LoopPopup(
    visible: Boolean,
    isEdit: Boolean,
    name: String,
    start: String,
    end: String,
    count: String,
    onNameChange: (String) -> Unit,
    onStartChange: (String) -> Unit,
    onEndChange: (String) -> Unit,
    onCountChange: (String) -> Unit,
    onPreview: () -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
) {
    if (!visible) return
    PopupShell(
        title = if (isEdit) "Edit Loop" else "New Log Entry",
        onClose = onClose,
        content = {
            HLFieldSet(
                label = "Name",
                value = name,
                onValueChange = onNameChange,
                placeholder = "e.g. Chorus Section",
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                HLFieldSet(
                    label = "Start",
                    value = start,
                    onValueChange = onStartChange,
                    placeholder = "e.g. 1:23",
                    modifier = Modifier.weight(1f),
                )
                HLFieldSet(
                    label = "End",
                    value = end,
                    onValueChange = onEndChange,
                    placeholder = "e.g. 2:45",
                    modifier = Modifier.weight(1f),
                )
            }
            HLFieldSet(
                label = "Times",
                value = count,
                onValueChange = onCountChange,
                placeholder = "e.g. 3",
                keyboardType = KeyboardType.Number,
            )
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                HLPillButton("Preview", onPreview, modifier = Modifier.weight(1f))
                HLPillButton("Save", onSave, modifier = Modifier.weight(1f))
            }
        }
    )
}
