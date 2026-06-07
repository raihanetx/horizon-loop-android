package com.horizonloop.app.data.model

data class Audio(
    val id: String,
    val title: String,
    val author: String,
    val sizeBytes: Long,
    val durationMs: Long,
    val hasSubtitle: Boolean,
    val isPinned: Boolean,
    val audioUrl: String,
    val coverUrl: String? = null,
)
