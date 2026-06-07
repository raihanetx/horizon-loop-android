package com.horizonloop.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.model.AiEngine
import com.horizonloop.app.ui.components.AudioCard
import com.horizonloop.app.ui.components.HLFilterChip
import com.horizonloop.app.ui.components.HLIconButton
import com.horizonloop.app.ui.components.HLPillButton
import com.horizonloop.app.ui.components.HLSearchInput
import com.horizonloop.app.ui.components.HomeHeader
import com.horizonloop.app.ui.components.PopupShell
import com.horizonloop.app.ui.components.HLFieldSet
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.Deep
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.Mid60
import com.horizonloop.app.ui.theme.Muted
import com.horizonloop.app.ui.theme.Surface
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onOpenAudio: (Audio) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Deep)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader(
                title = "Horizon Loop",
                tagline = "Master English by Listening",
                actions = {
                    HLIconButton(
                        icon = Icons.Filled.Tune,
                        contentDescription = "Toggle Filter Options",
                        onClick = { viewModel.toggleFilterDropdown() },
                    )
                    HLIconButton(
                        icon = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        onClick = { viewModel.openSettings() },
                    )
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 12.dp),
            ) {
                HLSearchInput(
                    value = state.search,
                    onValueChange = viewModel::onSearch,
                )
            }

            if (state.filterDropdownVisible) {
                FilterRow(
                    active = state.activeFilter,
                    onSelect = viewModel::applyFilter,
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                contentPadding = PaddingValues(top = 0.dp, bottom = 15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.filtered, key = { it.id }) { audio ->
                    AudioCard(
                        title = audio.title,
                        author = audio.author,
                        durationMs = audio.durationMs,
                        sizeBytes = audio.sizeBytes,
                        hasSubtitle = audio.hasSubtitle,
                        isPinned = audio.isPinned,
                        icon = Icons.Filled.Article,
                        onClick = { onOpenAudio(audio) },
                        onLongPress = { viewModel.togglePin(audio.id) },
                    )
                }
            }
        }

        if (state.settingsVisible) {
            SettingsPopupView(
                apiKey = state.apiKeyDraft,
                engine = state.engineDraft,
                onApiKeyChange = viewModel::setApiKeyDraft,
                onEngineChange = viewModel::setEngineDraft,
                onSave = viewModel::saveSettings,
                onClose = viewModel::closeSettings,
            )
        }
    }
}

@Composable
private fun FilterRow(active: HomeFilter, onSelect: (HomeFilter) -> Unit) {
    val scroll = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(bottom = 12.dp)
            .horizontalScroll(scroll),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HLFilterChip(label = "All", active = active == HomeFilter.ALL, onClick = { onSelect(HomeFilter.ALL) })
        HLFilterChip(label = "Size: High to Low", active = active == HomeFilter.SIZE_DESC, onClick = { onSelect(HomeFilter.SIZE_DESC) })
        HLFilterChip(label = "Size: Low to High", active = active == HomeFilter.SIZE_ASC, onClick = { onSelect(HomeFilter.SIZE_ASC) })
        HLFilterChip(label = "Subtitle: Yes", active = active == HomeFilter.SUBTITLE_YES, onClick = { onSelect(HomeFilter.SUBTITLE_YES) })
        HLFilterChip(label = "Subtitle: No", active = active == HomeFilter.SUBTITLE_NO, onClick = { onSelect(HomeFilter.SUBTITLE_NO) })
        HLFilterChip(label = "Pinned", active = active == HomeFilter.PINNED, onClick = { onSelect(HomeFilter.PINNED) })
    }
}

@Composable
private fun SettingsPopupView(
    apiKey: String,
    engine: AiEngine,
    onApiKeyChange: (String) -> Unit,
    onEngineChange: (AiEngine) -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
) {
    PopupShell(
        title = "Settings",
        onClose = onClose,
        content = {
            HLFieldSet(
                label = "API Key",
                value = apiKey,
                onValueChange = onApiKeyChange,
                placeholder = "Enter your API key...",
            )
            EngineSelector(engine = engine, onChange = onEngineChange)
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                HLPillButton("Save", onSave, modifier = Modifier.weight(1f))
                HLPillButton("Cancel", onClose, modifier = Modifier.weight(1f))
            }
        }
    )
}

@Composable
private fun EngineSelector(engine: AiEngine, onChange: (AiEngine) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, Muted, RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = "ENGINE",
            color = Mid,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 0.dp, top = 2.dp, bottom = 6.dp),
        )
        EngineDropdown(engine = engine, onChange = onChange)
    }
}

@Composable
private fun EngineDropdown(engine: AiEngine, onChange: (AiEngine) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = engine.displayName,
                color = Dark,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            AiEngine.entries.forEach { e ->
                DropdownMenuItem(
                    text = { Text(e.displayName) },
                    onClick = {
                        onChange(e)
                        expanded = false
                    },
                )
            }
        }
    }
}
