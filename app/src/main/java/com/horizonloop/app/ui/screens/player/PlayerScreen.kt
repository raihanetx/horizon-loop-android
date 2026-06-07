package com.horizonloop.app.ui.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Translate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.horizonloop.app.ui.components.CapsuleMenu
import com.horizonloop.app.ui.components.MenuItem
import com.horizonloop.app.ui.components.NavDrawer
import com.horizonloop.app.ui.components.NavItem
import com.horizonloop.app.ui.components.PlayerBottomCard
import com.horizonloop.app.ui.components.PlayerHeader
import com.horizonloop.app.ui.components.SendPopup
import com.horizonloop.app.ui.popups.DialogueSliderPopup
import com.horizonloop.app.ui.popups.LoopPopup
import com.horizonloop.app.ui.popups.NotePopup
import com.horizonloop.app.ui.screens.player.tabs.CleanTab
import com.horizonloop.app.ui.screens.player.tabs.DialogueTab
import com.horizonloop.app.ui.screens.player.tabs.LoopsTab
import com.horizonloop.app.ui.screens.player.tabs.NotesTab
import com.horizonloop.app.ui.screens.player.tabs.SpeedTab
import com.horizonloop.app.ui.theme.Deep
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerScreen(
    onBack: () -> Unit,
    viewModel: PlayerViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val audio = state.audio ?: return

    Box(modifier = Modifier.fillMaxSize().background(Deep)) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlayerHeader(
                title = audio.title,
                badge = if (state.audioMode) "AUDIO" else null,
                onBack = onBack,
                onMenu = viewModel::openCapsule,
            )

            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                when (state.tab) {
                    PlayerTab.CLEAN -> CleanTab(
                        dialogues = state.dialogues,
                        audioMode = state.audioMode,
                        currentMs = state.currentMs,
                        speedPopupText = state.speedPopupValue,
                        speedPopupVisible = state.speedPopupVisible,
                        onSpeedChange = viewModel::setSpeed,
                        onSpeedPopupShow = { viewModel.showSpeedPopup(it) },
                        onSpeedPopupHide = viewModel::hideSpeedPopup,
                        baseSpeed = state.speed,
                    )
                    PlayerTab.DIALOGUE -> DialogueTab(
                        dialogues = state.dialogues,
                        activeId = state.activeDialogueId,
                        onClick = viewModel::openDialogueSlider,
                    )
                    PlayerTab.LOOPS -> LoopsTab(
                        loops = state.loops,
                        deleteMode = state.loopDeleteMode,
                        onAdd = viewModel::openLoopAdd,
                        onEdit = viewModel::openLoopEdit,
                        onDeleteMode = viewModel::openLoopDelete,
                        onDelete = viewModel::deleteLoop,
                    )
                    PlayerTab.NOTES -> NotesTab(
                        notes = state.notes,
                        onAdd = viewModel::openNoteAdd,
                        onClick = viewModel::openNoteEdit,
                    )
                    PlayerTab.SPEED -> SpeedTab(
                        speed = state.speed,
                        onSpeedChange = viewModel::setSpeed,
                    )
                }
            }

            PlayerBottomCard(
                title = audio.title,
                isPlaying = state.isPlaying,
                currentMs = state.currentMs,
                totalMs = state.totalMs,
                speed = state.speed,
                mode = if (state.audioMode) "Audio" else "Clean",
                loopCount = state.loops.size,
                onRewind = { viewModel.seek(-5000) },
                onForward = { viewModel.seek(5000) },
                onPlayPause = viewModel::togglePlay,
                onSeek = viewModel::seek,
            )
        }

        NavDrawer(
            title = "Navigation",
            visible = state.navDrawerVisible,
            onDismiss = viewModel::closeNavDrawer,
            items = listOf(
                NavItem("home", Icons.Filled.Home, "Home", onClick = onBack),
                NavItem("speed", Icons.Filled.Bolt, "Speed", value = "${state.speed}x", onClick = { viewModel.setTab(PlayerTab.SPEED) }),
                NavItem("audio-mode", Icons.Filled.Headphones, "Audio", value = if (state.audioMode) "On" else "Off", onClick = viewModel::toggleAudioMode),
                NavItem("clean", Icons.Filled.ClosedCaption, "Subtitle", onClick = { viewModel.setTab(PlayerTab.CLEAN) }),
                NavItem("save", Icons.Filled.Bookmark, "Save", onClick = { viewModel.setTab(PlayerTab.DIALOGUE) }),
                NavItem("loop", Icons.Filled.Repeat, "Save time frame", onClick = { viewModel.setTab(PlayerTab.LOOPS) }),
                NavItem("notes", Icons.Filled.NoteAlt, "Notes", onClick = { viewModel.setTab(PlayerTab.NOTES) }),
            ),
        )

        CapsuleMenu(
            visible = state.capsuleMenuVisible,
            onDismiss = viewModel::closeCapsule,
            items = listOf(
                MenuItem("subtitle", Icons.Filled.ClosedCaption, "Subtitle", "Enable or disable subtitles display",
                    onClick = { viewModel.setTab(PlayerTab.CLEAN) }),
                MenuItem("list", Icons.Filled.Article, "List", "View full dialogue and transcript list",
                    onClick = { viewModel.setTab(PlayerTab.DIALOGUE) }),
                MenuItem("loop", Icons.Filled.Repeat, "Save time frame", "Set start and end time markers",
                    onClick = { viewModel.setTab(PlayerTab.LOOPS) }),
                MenuItem("note", Icons.Filled.NoteAlt, "Note", "Add and manage your personal notes",
                    onClick = { viewModel.setTab(PlayerTab.NOTES) }),
                MenuItem("audio", Icons.Filled.Headphones, "Audio", "Toggle audio only mode for listening",
                    onClick = viewModel::toggleAudioMode, active = state.audioMode),
                MenuItem("translate", Icons.Filled.Translate, "Translate", "Generate English and Bangla subtitles",
                    onClick = viewModel::startTranslate),
            ),
        )

        SendPopup(visible = state.sendPopupVisible, text = state.sendPopupText)

        DialogueSliderPopup(
            visible = state.dialogueSliderVisible,
            dialogue = state.selectedDialogue,
            onClose = viewModel::closeDialogueSlider,
        )

        NotePopup(
            visible = state.notePopupVisible,
            title = if (state.noteEditingId != null) "Edit Note" else "Add Note",
            text = state.noteDraft,
            onTextChange = viewModel::setNoteDraft,
            onSave = viewModel::saveNote,
            onClose = viewModel::closeNote,
        )

        LoopPopup(
            visible = state.loopPopupVisible,
            isEdit = state.isLoopEdit,
            name = state.loopDraftName,
            start = state.loopDraftStart,
            end = state.loopDraftEnd,
            count = state.loopDraftCount,
            onNameChange = viewModel::setLoopName,
            onStartChange = viewModel::setLoopStart,
            onEndChange = viewModel::setLoopEnd,
            onCountChange = viewModel::setLoopCount,
            onPreview = viewModel::previewLoop,
            onSave = viewModel::saveLoop,
            onClose = viewModel::closeLoop,
        )
    }
}
