package com.horizonloop.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.ui.screens.home.HomeScreen
import com.horizonloop.app.ui.screens.player.PlayerScreen
import com.horizonloop.app.ui.screens.player.PlayerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppRoot() {
    val playerViewModel: PlayerViewModel = koinViewModel()
    val state by playerViewModel.state.collectAsState()
    val audio = state.audio

    if (audio != null) {
        PlayerScreen(
            onBack = { playerViewModel.close() },
            viewModel = playerViewModel,
        )
    } else {
        HomeScreen(
            onOpenAudio = { playerViewModel.openAudio(it) },
        )
    }
}
