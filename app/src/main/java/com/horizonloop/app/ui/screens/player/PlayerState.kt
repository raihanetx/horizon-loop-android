package com.horizonloop.app.ui.screens.player

import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.model.Dialogue
import com.horizonloop.app.data.model.Loop
import com.horizonloop.app.data.model.Note

enum class PlayerTab { CLEAN, DIALOGUE, LOOPS, NOTES, SPEED }

data class PlayerState(
    val audio: Audio? = null,
    val isPlaying: Boolean = false,
    val currentMs: Long = 0,
    val totalMs: Long = 0,
    val speed: Float = 1f,
    val audioMode: Boolean = false,
    val tab: PlayerTab = PlayerTab.CLEAN,
    val notes: List<Note> = emptyList(),
    val loops: List<Loop> = emptyList(),
    val dialogues: List<Dialogue> = emptyList(),
    val activeDialogueId: String? = null,
    val navDrawerVisible: Boolean = false,
    val capsuleMenuVisible: Boolean = false,
    val sendPopupVisible: Boolean = false,
    val sendPopupText: String = "",
    val dialogueSliderVisible: Boolean = false,
    val selectedDialogue: Dialogue? = null,
    val notePopupVisible: Boolean = false,
    val loopPopupVisible: Boolean = false,
    val isLoopEdit: Boolean = false,
    val loopDeleteMode: Boolean = false,
    val noteDraft: String = "",
    val noteEditingId: String? = null,
    val loopDraftName: String = "",
    val loopDraftStart: String = "",
    val loopDraftEnd: String = "",
    val loopDraftCount: String = "",
    val loopEditingId: String? = null,
    val speedPopupVisible: Boolean = false,
    val speedPopupValue: String = "1x",
)
