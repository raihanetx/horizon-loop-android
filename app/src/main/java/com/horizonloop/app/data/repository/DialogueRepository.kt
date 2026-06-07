package com.horizonloop.app.data.repository

import com.horizonloop.app.data.model.Dialogue
import com.horizonloop.app.data.sample.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted

class DialogueRepository {
    private val _all = MutableStateFlow(SampleData.dialogues)
    val all: StateFlow<List<Dialogue>> = _all.asStateFlow()

    fun forAudio(audioId: String): List<Dialogue> = _all.value.filter { it.audioId == audioId }

    fun replaceForAudio(audioId: String, items: List<Dialogue>) {
        val others = _all.value.filter { it.audioId != audioId }
        _all.value = others + items
    }
}
