package com.horizonloop.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizonloop.app.data.model.AiEngine
import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.repository.AudioRepository
import com.horizonloop.app.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val audioRepo: AudioRepository by inject()
    private val settingsRepo: SettingsRepository by inject()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            audioRepo.audios.collect { all ->
                _state.update { it.copy(audios = all, filtered = applyFilters(all, it.search, it.activeFilter)) }
            }
        }
        viewModelScope.launch {
            settingsRepo.settings.collectLatest { s ->
                _state.update {
                    if (it.settingsVisible) it else it.copy(
                        apiKeyDraft = s.apiKey,
                        engineDraft = s.engine,
                    )
                }
            }
        }
    }

    fun onSearch(q: String) {
        _state.update { it.copy(search = q, filtered = applyFilters(it.audios, q, it.activeFilter)) }
    }

    fun toggleFilterDropdown() {
        _state.update { it.copy(filterDropdownVisible = !it.filterDropdownVisible) }
    }

    fun applyFilter(filter: HomeFilter) {
        _state.update {
            val newFilter = if (it.activeFilter == filter || filter == HomeFilter.ALL) HomeFilter.ALL else filter
            it.copy(
                activeFilter = newFilter,
                filtered = applyFilters(it.audios, it.search, newFilter),
                filterDropdownVisible = false,
            )
        }
    }

    fun togglePin(id: String) {
        audioRepo.togglePinned(id)
    }

    fun openSettings() {
        viewModelScope.launch {
            settingsRepo.settings.collect { s ->
                _state.update { it.copy(settingsVisible = true, apiKeyDraft = s.apiKey, engineDraft = s.engine) }
                return@collect
            }
        }
    }

    fun closeSettings() {
        _state.update { it.copy(settingsVisible = false) }
    }

    fun setApiKeyDraft(value: String) {
        _state.update { it.copy(apiKeyDraft = value) }
    }

    fun setEngineDraft(value: AiEngine) {
        _state.update { it.copy(engineDraft = value) }
    }

    fun saveSettings() {
        val s = _state.value
        viewModelScope.launch {
            settingsRepo.setApiKey(s.apiKeyDraft)
            settingsRepo.setEngine(s.engineDraft)
            _state.update { it.copy(settingsVisible = false) }
        }
    }

    private fun applyFilters(audios: List<Audio>, query: String, filter: HomeFilter): List<Audio> {
        var list = audios
        if (query.isNotBlank()) {
            val q = query.lowercase()
            list = list.filter { it.title.lowercase().contains(q) }
        }
        list = when (filter) {
            HomeFilter.ALL -> list
            HomeFilter.SIZE_DESC -> list.sortedByDescending { it.sizeBytes }
            HomeFilter.SIZE_ASC -> list.sortedBy { it.sizeBytes }
            HomeFilter.SUBTITLE_YES -> list.filter { it.hasSubtitle }
            HomeFilter.SUBTITLE_NO -> list.filter { !it.hasSubtitle }
            HomeFilter.PINNED -> list.filter { it.isPinned }
        }
        return list
    }
}
