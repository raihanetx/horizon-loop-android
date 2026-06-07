package com.horizonloop.app.data.sample

import com.horizonloop.app.data.model.Audio
import com.horizonloop.app.data.model.Dialogue
import com.horizonloop.app.data.model.Loop
import com.horizonloop.app.data.model.Note

object SampleData {
    val audios: List<Audio> = listOf(
        Audio(
            id = "a1",
            title = "The Power of Habit",
            author = "Charles Duhigg",
            sizeBytes = 28_500_000,
            durationMs = 9 * 60_000L + 32_000L,
            hasSubtitle = true,
            isPinned = true,
            audioUrl = "https://example.com/audio/power-of-habit.mp3",
        ),
        Audio(
            id = "a2",
            title = "Atomic Habits — Ch.1",
            author = "James Clear",
            sizeBytes = 12_200_000,
            durationMs = 4 * 60_000L + 8_000L,
            hasSubtitle = true,
            isPinned = false,
            audioUrl = "https://example.com/audio/atomic-habits-1.mp3",
        ),
        Audio(
            id = "a3",
            title = "Deep Work",
            author = "Cal Newport",
            sizeBytes = 41_300_000,
            durationMs = 14 * 60_000L + 21_000L,
            hasSubtitle = false,
            isPinned = true,
            audioUrl = "https://example.com/audio/deep-work.mp3",
        ),
        Audio(
            id = "a4",
            title = "Thinking, Fast and Slow",
            author = "Daniel Kahneman",
            sizeBytes = 18_750_000,
            durationMs = 6 * 60_000L + 14_000L,
            hasSubtitle = true,
            isPinned = false,
            audioUrl = "https://example.com/audio/thinking-fast-slow.mp3",
        ),
        Audio(
            id = "a5",
            title = "The Lean Startup",
            author = "Eric Ries",
            sizeBytes = 8_900_000,
            durationMs = 3 * 60_000L + 2_000L,
            hasSubtitle = true,
            isPinned = false,
            audioUrl = "https://example.com/audio/lean-startup.mp3",
        ),
        Audio(
            id = "a6",
            title = "Mindset: The New Psychology",
            author = "Carol S. Dweck",
            sizeBytes = 22_400_000,
            durationMs = 7 * 60_000L + 45_000L,
            hasSubtitle = false,
            isPinned = false,
            audioUrl = "https://example.com/audio/mindset.mp3",
        ),
        Audio(
            id = "a7",
            title = "Can't Hurt Me",
            author = "David Goggins",
            sizeBytes = 31_200_000,
            durationMs = 10 * 60_000L + 27_000L,
            hasSubtitle = true,
            isPinned = true,
            audioUrl = "https://example.com/audio/cant-hurt-me.mp3",
        ),
        Audio(
            id = "a8",
            title = "Range: Why Generalists Triumph",
            author = "David Epstein",
            sizeBytes = 15_600_000,
            durationMs = 5 * 60_000L + 18_000L,
            hasSubtitle = true,
            isPinned = false,
            audioUrl = "https://example.com/audio/range.mp3",
        ),
    )

    val dialogues: List<Dialogue> = listOf(
        Dialogue("d1", "a1", 0, 4_000, "Habits are the small decisions you make and the actions you perform every day.", "অভ্যাস হলো প্রতিদিনের ছোট সিদ্ধান্ত এবং কাজ।"),
        Dialogue("d2", "a1", 4_000, 9_000, "According to researchers, roughly 40 to 45 percent of what we do every day is habit.", "গবেষকদের মতে, আমাদের দৈনিক কাজের ৪০-৪৫% অভ্যাস।"),
        Dialogue("d3", "a1", 9_000, 14_500, "Once you understand that habits can be rebuilt, you gain freedom.", "একবার বুঝলে অভ্যাস পরিবর্তনযোগ্য, আপনি স্বাধীনতা পান।"),
        Dialogue("d4", "a1", 14_500, 20_000, "The cue, the routine, and the reward form a loop that drives behavior.", "ইঙ্গিত, রুটিন এবং পুরস্কার একটি চক্র তৈরি করে।"),
    )

    val loops: List<Loop> = listOf(
        Loop("l1", "a1", "Intro — Key idea", 0, 9_000, 3),
        Loop("l2", "a1", "Cue-routine-reward", 14_500, 24_000, 5),
    )

    val notes: List<Note> = listOf(
        Note("n1", "a1", "Focus on the keystone habit. Start small.", 8_400),
        Note("n2", "a1", "Quote for journaling: 'freedom to choose'", 17_200),
    )
}
