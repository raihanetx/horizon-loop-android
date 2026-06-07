package com.horizonloop.app.data.repository

import com.horizonloop.app.data.model.Note
import com.horizonloop.app.data.sample.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class NotesRepository {
    private val _all = MutableStateFlow(SampleData.notes)
    val all: StateFlow<List<Note>> = _all.asStateFlow()

    fun forAudio(audioId: String): List<Note> = _all.value.filter { it.audioId == audioId }

    fun add(note: Note) { _all.value = _all.value + note }

    fun update(note: Note) {
        _all.value = _all.value.map { if (it.id == note.id) note else it }
    }

    fun delete(id: String) {
        _all.value = _all.value.filterNot { it.id == id }
    }

    fun nextId() = UUID.randomUUID().toString()
}
