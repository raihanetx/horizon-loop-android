package com.horizonloop.app.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizonloop.app.audio.AudioPlayerController
import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.model.Dialogue
import com.horizonloop.app.data.model.Loop
import com.horizonloop.app.data.model.Note
import com.horizonloop.app.data.repository.AudioRepository
import com.horizonloop.app.data.repository.DialogueRepository
import com.horizonloop.app.data.repository.LoopRepository
import com.horizonloop.app.data.repository.NotesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayerViewModel(
    private val player: AudioPlayerController,
) : ViewModel(), KoinComponent {
    private val audioRepo: AudioRepository by inject()
    private val notesRepo: NotesRepository by inject()
    private val loopsRepo: LoopRepository by inject()
    private val dialogueRepo: DialogueRepository by inject()

    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            player.state.collect { ps ->
                _state.update {
                    it.copy(
                        isPlaying = ps.isPlaying,
                        currentMs = ps.currentMs,
                        totalMs = if (ps.totalMs > 0) ps.totalMs else it.totalMs,
                        speed = ps.speed,
                    )
                }
            }
        }
    }

    fun openAudio(audio: Audio) {
        val notes = notesRepo.forAudio(audio.id)
        val loops = loopsRepo.forAudio(audio.id)
        val dialogues = dialogueRepo.forAudio(audio.id)
        player.load(audio)
        _state.update {
            it.copy(
                audio = audio,
                totalMs = audio.durationMs,
                currentMs = 0,
                isPlaying = false,
                speed = 1f,
                tab = PlayerTab.CLEAN,
                notes = notes,
                loops = loops,
                dialogues = dialogues,
                activeDialogueId = null,
                notePopupVisible = false,
                loopPopupVisible = false,
            )
        }
    }

    fun close() {
        player.pause()
        _state.update {
            it.copy(
                audio = null,
                isPlaying = false,
                capsuleMenuVisible = false,
                navDrawerVisible = false,
            )
        }
    }

    fun togglePlay() {
        if (_state.value.isPlaying) player.pause() else player.play()
    }

    fun seek(deltaMs: Long) {
        player.seekTo(_state.value.currentMs + deltaMs)
    }

    fun seekTo(ms: Long) {
        player.seekTo(ms)
    }

    fun setSpeed(value: Float) {
        player.setSpeed(value)
        _state.update { it.copy(speedPopupValue = "${value}x") }
    }

    fun showSpeedPopup(value: String) {
        _state.update { it.copy(speedPopupVisible = true, speedPopupValue = value) }
    }

    fun hideSpeedPopup() {
        _state.update { it.copy(speedPopupVisible = false) }
    }

    fun setTab(tab: PlayerTab) {
        _state.update { it.copy(tab = tab, navDrawerVisible = false) }
    }

    fun toggleAudioMode() {
        _state.update { it.copy(audioMode = !it.audioMode, capsuleMenuVisible = false) }
    }

    fun openNavDrawer() { _state.update { it.copy(navDrawerVisible = true) } }
    fun closeNavDrawer() { _state.update { it.copy(navDrawerVisible = false) } }
    fun openCapsule() { _state.update { it.copy(capsuleMenuVisible = true) } }
    fun closeCapsule() { _state.update { it.copy(capsuleMenuVisible = false) } }

    fun openNoteAdd() {
        _state.update { it.copy(notePopupVisible = true, noteDraft = "", noteEditingId = null) }
    }

    fun openNoteEdit(note: Note) {
        _state.update { it.copy(notePopupVisible = true, noteDraft = note.text, noteEditingId = note.id) }
    }

    fun closeNote() {
        _state.update { it.copy(notePopupVisible = false, noteDraft = "", noteEditingId = null) }
    }

    fun setNoteDraft(value: String) {
        _state.update { it.copy(noteDraft = value) }
    }

    fun saveNote() {
        val s = _state.value
        val audioId = s.audio?.id ?: return
        val text = s.noteDraft.trim()
        if (text.isEmpty()) return
        if (s.noteEditingId != null) {
            val existing = s.notes.firstOrNull { it.id == s.noteEditingId } ?: return
            notesRepo.update(existing.copy(text = text))
            _state.update {
                it.copy(notes = notesRepo.forAudio(audioId), notePopupVisible = false, noteEditingId = null, noteDraft = "")
            }
        } else {
            val n = Note(
                id = notesRepo.nextId(),
                audioId = audioId,
                text = text,
                timestampMs = s.currentMs,
            )
            notesRepo.add(n)
            _state.update {
                it.copy(notes = notesRepo.forAudio(audioId), notePopupVisible = false, noteDraft = "")
            }
        }
    }

    fun deleteNote(id: String) {
        val audioId = _state.value.audio?.id ?: return
        notesRepo.delete(id)
        _state.update { it.copy(notes = notesRepo.forAudio(audioId)) }
    }

    fun openLoopAdd() {
        _state.update {
            it.copy(
                loopPopupVisible = true,
                isLoopEdit = false,
                loopDraftName = "",
                loopDraftStart = "",
                loopDraftEnd = "",
                loopDraftCount = "",
                loopEditingId = null,
            )
        }
    }

    fun openLoopEdit(loop: Loop) {
        _state.update {
            it.copy(
                loopPopupVisible = true,
                isLoopEdit = true,
                loopEditingId = loop.id,
                loopDraftName = loop.name,
                loopDraftStart = formatMs(loop.startMs),
                loopDraftEnd = formatMs(loop.endMs),
                loopDraftCount = loop.repeatCount.toString(),
            )
        }
    }

    fun openLoopDelete() {
        _state.update { it.copy(loopDeleteMode = !it.loopDeleteMode) }
    }

    fun closeLoop() {
        _state.update {
            it.copy(
                loopPopupVisible = false,
                isLoopEdit = false,
                loopEditingId = null,
                loopDraftName = "",
                loopDraftStart = "",
                loopDraftEnd = "",
                loopDraftCount = "",
            )
        }
    }

    fun setLoopName(v: String) { _state.update { it.copy(loopDraftName = v) } }
    fun setLoopStart(v: String) { _state.update { it.copy(loopDraftStart = v) } }
    fun setLoopEnd(v: String) { _state.update { it.copy(loopDraftEnd = v) } }
    fun setLoopCount(v: String) { _state.update { it.copy(loopDraftCount = v) } }

    fun previewLoop() {
        val s = _state.value
        val (start, end) = parseRange(s.loopDraftStart, s.loopDraftEnd) ?: return
        val count = s.loopDraftCount.toIntOrNull() ?: 1
        player.startLoop(start, end, count)
        _state.update {
            it.copy(
                loopPopupVisible = false,
                speedPopupVisible = true,
                speedPopupValue = "1x",
            )
        }
    }

    fun saveLoop() {
        val s = _state.value
        val audioId = s.audio?.id ?: return
        val (start, end) = parseRange(s.loopDraftStart, s.loopDraftEnd) ?: return
        val count = s.loopDraftCount.toIntOrNull() ?: 1
        if (s.loopEditingId != null) {
            val existing = s.loops.firstOrNull { it.id == s.loopEditingId } ?: return
            loopsRepo.update(
                existing.copy(
                    name = s.loopDraftName.ifBlank { "Loop" },
                    startMs = start,
                    endMs = end,
                    repeatCount = count,
                )
            )
        } else {
            loopsRepo.add(
                Loop(
                    id = loopsRepo.nextId(),
                    audioId = audioId,
                    name = s.loopDraftName.ifBlank { "Loop" },
                    startMs = start,
                    endMs = end,
                    repeatCount = count,
                )
            )
        }
        _state.update {
            it.copy(loops = loopsRepo.forAudio(audioId), loopPopupVisible = false, isLoopEdit = false)
        }
    }

    fun deleteLoop(id: String) {
        val audioId = _state.value.audio?.id ?: return
        loopsRepo.delete(id)
        _state.update { it.copy(loops = loopsRepo.forAudio(audioId)) }
    }

    fun openDialogueSlider(d: Dialogue) {
        _state.update { it.copy(dialogueSliderVisible = true, selectedDialogue = d, activeDialogueId = d.id) }
    }

    fun closeDialogueSlider() {
        _state.update { it.copy(dialogueSliderVisible = false) }
    }

    fun startTranslate() {
        _state.update { it.copy(capsuleMenuVisible = false, sendPopupVisible = true, sendPopupText = "Sending...") }
        viewModelScope.launch {
            delay(1500)
            _state.update { it.copy(sendPopupVisible = false, sendPopupText = "") }
        }
    }

    private fun parseRange(start: String, end: String): Pair<Long, Long>? {
        val s = parseTime(start) ?: return null
        val e = parseTime(end) ?: return null
        if (e <= s) return null
        return s to e
    }

    private fun parseTime(input: String): Long? {
        if (input.isBlank()) return null
        val parts = input.trim().split(":")
        return when (parts.size) {
            1 -> parts[0].toLongOrNull()?.times(1000)
            2 -> {
                val m = parts[0].toLongOrNull() ?: return null
                val s = parts[1].toLongOrNull() ?: return null
                (m * 60 + s) * 1000
            }
            3 -> {
                val h = parts[0].toLongOrNull() ?: return null
                val m = parts[1].toLongOrNull() ?: return null
                val s = parts[2].toLongOrNull() ?: return null
                (h * 3600 + m * 60 + s) * 1000
            }
            else -> null
        }
    }

    private fun formatMs(ms: Long): String {
        val total = ms / 1000
        val m = total / 60
        val s = total % 60
        return "%d:%02d".format(m, s)
    }
}
