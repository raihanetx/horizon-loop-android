package com.horizonloop.app.data.repository

import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.sample.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioRepository {
    private val _audios = MutableStateFlow(SampleData.audios)
    val audios: StateFlow<List<Audio>> = _audios.asStateFlow()

    fun getById(id: String): Audio? = _audios.value.firstOrNull { it.id == id }

    fun togglePinned(id: String) {
        _audios.value = _audios.value.map {
            if (it.id == id) it.copy(isPinned = !it.isPinned) else it
        }
    }
}
