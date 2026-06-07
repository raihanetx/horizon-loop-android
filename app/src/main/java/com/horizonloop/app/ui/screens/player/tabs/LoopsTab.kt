package com.horizonloop.app.ui.screens.player.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.data.model.Loop
import com.horizonloop.app.ui.components.FAB
import com.horizonloop.app.ui.components.LoopCard
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.util.HLFormat

@Composable
fun LoopsTab(
    loops: List<Loop>,
    deleteMode: Boolean,
    onAdd: () -> Unit,
    onEdit: (Loop) -> Unit,
    onDeleteMode: () -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (loops.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No loops yet.", color = Mid, fontSize = 12.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp),
                contentPadding = PaddingValues(start = 0.dp, end = 0.dp, top = 8.dp, bottom = 150.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(loops, key = { it.id }) { loop ->
                    Box(modifier = Modifier.padding(horizontal = 0.dp)) {
                        LoopCard(
                            name = loop.name,
                            rangeText = "${HLFormat.time(loop.startMs)} - ${HLFormat.time(loop.endMs)}",
                            countText = "${loop.repeatCount}x",
                            onClick = { if (deleteMode) onDelete(loop.id) else onEdit(loop) },
                            trailing = {
                                Icon(
                                    imageVector = if (deleteMode) Icons.Filled.Delete else Icons.Filled.Edit,
                                    contentDescription = null,
                                    tint = Mid,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        )
                    }
                }
            }
        }

        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loops.isNotEmpty()) {
                FAB(
                    icon = if (deleteMode) Icons.Filled.Delete else Icons.Filled.Edit,
                    onClick = onDeleteMode,
                    background = if (deleteMode) com.horizonloop.app.ui.theme.Mid else com.horizonloop.app.ui.theme.Muted,
                )
            }
            FAB(
                icon = Icons.Filled.Add,
                onClick = onAdd,
                background = com.horizonloop.app.ui.theme.Muted,
            )
        }
    }
}
