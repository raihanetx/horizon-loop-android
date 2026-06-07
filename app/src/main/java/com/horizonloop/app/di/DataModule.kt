package com.horizonloop.app.di

import com.horizonloop.app.audio.AudioPlayerController
import com.horizonloop.app.data.local.SettingsDataStore
import com.horizonloop.app.data.repository.AudioRepository
import com.horizonloop.app.data.repository.DialogueRepository
import com.horizonloop.app.data.repository.LoopRepository
import com.horizonloop.app.data.repository.NotesRepository
import com.horizonloop.app.data.repository.SettingsRepository
import com.horizonloop.app.ui.screens.home.HomeViewModel
import com.horizonloop.app.ui.screens.player.PlayerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { SettingsDataStore(androidContext()) }
    single { SettingsRepository(get()) }
    single { AudioRepository() }
    single { NotesRepository() }
    single { LoopRepository() }
    single { DialogueRepository() }
    single { AudioPlayerController(androidContext()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { PlayerViewModel(get()) }
}
