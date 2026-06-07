package com.horizonloop.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.horizonloop.app.data.model.AiEngine
import com.horizonloop.app.data.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "horizon_settings")

class SettingsDataStore(private val context: Context) {

    private val keyApi = stringPreferencesKey("api_key")
    private val keyEngine = stringPreferencesKey("ai_engine")

    val settings: Flow<Settings> = context.dataStore.data.map { prefs ->
        Settings(
            apiKey = prefs[keyApi].orEmpty(),
            engine = AiEngine.fromId(prefs[keyEngine]),
        )
    }

    suspend fun updateApiKey(value: String) {
        context.dataStore.edit { it[keyApi] = value }
    }

    suspend fun updateEngine(value: AiEngine) {
        context.dataStore.edit { it[keyEngine] = value.id }
    }
}
