package com.horizonloop.app.data.repository

import com.horizonloop.app.data.model.Loop
import com.horizonloop.app.data.sample.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class LoopRepository {
    private val _all = MutableStateFlow(SampleData.loops)
    val all: StateFlow<List<Loop>> = _all.asStateFlow()

    fun forAudio(audioId: String): List<Loop> = _all.value.filter { it.audioId == audioId }

    fun add(loop: Loop) { _all.value = _all.value + loop }

    fun update(loop: Loop) {
        _all.value = _all.value.map { if (it.id == loop.id) loop else it }
    }

    fun delete(id: String) {
        _all.value = _all.value.filterNot { it.id == id }
    }

    fun nextId() = UUID.randomUUID().toString()
}
