package com.horizonloop.app.data.repository

import com.horizonloop.app.data.local.SettingsDataStore
import com.horizonloop.app.data.model.AiEngine
import com.horizonloop.app.data.model.Settings
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val store: SettingsDataStore) {
    val settings: Flow<Settings> = store.settings

    suspend fun setApiKey(value: String) = store.updateApiKey(value)
    suspend fun setEngine(value: AiEngine) = store.updateEngine(value)
}
