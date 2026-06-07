package com.horizonloop.app.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.exoplayer.ExoPlayer
import com.horizonloop.app.data.model.Audio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentMs: Long = 0,
    val totalMs: Long = 0,
    val speed: Float = 1f,
    val loop: LoopState? = null,
)

data class LoopState(
    val startMs: Long,
    val endMs: Long,
    val remainingRepeats: Int,
    val totalRepeats: Int,
)

class AudioPlayerController(private val context: Context) {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var tickJob: Job? = null
    private var currentAudio: Audio? = null
    private var loopState: LoopState? = null

    private val _state = MutableStateFlow(PlayerState(totalMs = 0))
    val state: StateFlow<PlayerState> = _state.asStateFlow()

    fun load(audio: Audio) {
        currentAudio = audio
        player.setMediaItem(MediaItem.fromUri(audio.audioUrl))
        player.prepare()
        _state.value = PlayerState(totalMs = audio.durationMs)
    }

    fun play() {
        if (currentAudio == null) return
        player.play()
        _state.value = _state.value.copy(isPlaying = true)
        startTicking()
    }

    fun pause() {
        player.pause()
        _state.value = _state.value.copy(isPlaying = false)
        stopTicking()
    }

    fun seekTo(ms: Long) {
        val clamped = ms.coerceIn(0, _state.value.totalMs)
        player.seekTo(clamped)
        _state.value = _state.value.copy(currentMs = clamped)
    }

    fun setSpeed(value: Float) {
        val clamped = value.coerceIn(0.25f, 2f)
        player.playbackParameters = PlaybackParameters(clamped)
        _state.value = _state.value.copy(speed = clamped)
    }

    fun startLoop(startMs: Long, endMs: Long, repeatCount: Int) {
        if (endMs <= startMs) return
        seekTo(startMs)
        loopState = LoopState(startMs, endMs, repeatCount, repeatCount)
        _state.value = _state.value.copy(loop = loopState)
        if (!_state.value.isPlaying) play()
    }

    fun release() {
        stopTicking()
        player.release()
        scope.cancel()
    }

    private fun startTicking() {
        if (tickJob?.isActive == true) return
        tickJob = scope.launch {
            while (_state.value.isPlaying) {
                val pos = player.currentPosition.coerceAtLeast(0)
                val total = player.duration.takeIf { it > 0 } ?: _state.value.totalMs
                val loop = loopState

                if (loop != null && pos >= loop.endMs) {
                    val next = loop.remainingRepeats - 1
                    if (next <= 0) {
                        loopState = null
                        _state.value = _state.value.copy(currentMs = pos, totalMs = total, loop = null)
                    } else {
                        loopState = loop.copy(remainingRepeats = next)
                        seekTo(loop.startMs)
                        _state.value = _state.value.copy(loop = loopState)
                    }
                } else {
                    _state.value = _state.value.copy(currentMs = pos, totalMs = total)
                }
                delay(100)
            }
        }
    }

    private fun stopTicking() {
        tickJob?.cancel()
        tickJob = null
    }
}
