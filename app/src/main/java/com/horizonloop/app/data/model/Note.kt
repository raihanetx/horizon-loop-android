package com.horizonloop.app.data.model

data class Note(
    val id: String,
    val audioId: String,
    val text: String,
    val timestampMs: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
)
