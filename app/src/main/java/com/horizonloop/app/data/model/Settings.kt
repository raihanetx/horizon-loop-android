package com.horizonloop.app.data.model

enum class AiEngine(val displayName: String, val id: String) {
    GPT_4O("GPT-4o", "gpt-4o"),
    GPT_4O_MINI("GPT-4o Mini", "gpt-4o-mini"),
    GPT_3_5_TURBO("GPT-3.5 Turbo", "gpt-3.5-turbo"),
    CLAUDE_3_OPUS("Claude 3 Opus", "claude-3-opus"),
    CLAUDE_3_SONNET("Claude 3 Sonnet", "claude-3-sonnet"),
    GEMINI_PRO("Gemini Pro", "gemini-pro"),
    GROQ_WHISPER("Whisper (Groq)", "whisper-large-v3"),
    GROQ_LLAMA("Llama 3 (Groq)", "llama-3.3-70b");

    companion object {
        fun fromId(id: String?): AiEngine =
            entries.firstOrNull { it.id == id } ?: GPT_4O
    }
}

data class Settings(
    val apiKey: String = "",
    val engine: AiEngine = AiEngine.GPT_4O,
)
