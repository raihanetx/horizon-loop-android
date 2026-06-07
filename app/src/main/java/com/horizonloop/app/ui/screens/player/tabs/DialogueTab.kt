package com.horizonloop.app.ui.screens.player.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.data.model.Dialogue
import com.horizonloop.app.ui.components.DialogueCard
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.util.HLFormat

@Composable
fun DialogueTab(
    dialogues: List<Dialogue>,
    activeId: String?,
    onClick: (Dialogue) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (dialogues.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No dialogues available", color = Mid, fontSize = 12.sp)
        }
        return
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp, 12.dp, 12.dp, 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(dialogues, key = { it.id }) { d ->
            DialogueCard(
                startTimeText = HLFormat.time(d.startMs),
                english = d.english,
                bangla = d.bangla,
                selected = d.id == activeId,
                onClick = { onClick(d) },
            )
        }
    }
}
