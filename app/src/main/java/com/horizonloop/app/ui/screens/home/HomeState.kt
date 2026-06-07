package com.horizonloop.app.ui.screens.home

import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.model.AiEngine

enum class HomeFilter {
    ALL, SIZE_DESC, SIZE_ASC, SUBTITLE_YES, SUBTITLE_NO, PINNED
}

data class HomeState(
    val audios: List<Audio> = emptyList(),
    val filtered: List<Audio> = emptyList(),
    val search: String = "",
    val activeFilter: HomeFilter = HomeFilter.ALL,
    val filterDropdownVisible: Boolean = false,
    val settingsVisible: Boolean = false,
    val apiKeyDraft: String = "",
    val engineDraft: AiEngine = AiEngine.GPT_4O,
)
