package com.horizonloop.app.ui.screens.player.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.data.model.Note
import com.horizonloop.app.ui.components.FAB
import com.horizonloop.app.ui.components.NoteCard
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.util.HLFormat

@Composable
fun NotesTab(
    notes: List<Note>,
    onAdd: () -> Unit,
    onClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (notes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No notes yet.", color = Mid, fontSize = 12.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 150.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteCard(
                        text = note.text,
                        onClick = { onClick(note) },
                        trailing = {
                            if (note.timestampMs != null) {
                                Text(
                                    text = HLFormat.time(note.timestampMs),
                                    color = Mid,
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    )
                }
            }
        }
        FAB(
            icon = Icons.Filled.Add,
            onClick = onAdd,
            background = com.horizonloop.app.ui.theme.Muted,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
        )
    }
}
