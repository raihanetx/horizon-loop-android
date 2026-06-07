package com.horizonloop.app.data.model

data class Dialogue(
    val id: String,
    val audioId: String,
    val startMs: Long,
    val endMs: Long,
    val english: String,
    val bangla: String,
)
