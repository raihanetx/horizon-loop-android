package com.horizonloop.app.data.model

data class Loop(
    val id: String,
    val audioId: String,
    val name: String,
    val startMs: Long,
    val endMs: Long,
    val repeatCount: Int,
)
