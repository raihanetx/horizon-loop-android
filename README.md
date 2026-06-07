# Horizon Loop — Android (Jetpack Compose)

Pixel-perfect Android conversion of the Horizon Loop HTML prototype.

**Phase 0–9 complete.** All 10 phases of the conversion plan have been implemented.

---

## Project Status

| Phase | Description | Status |
|---|---|---|
| 0 | Project setup (Gradle, manifest, Koin) | ✅ |
| 1 | Design system (colors, typography, spacing, shapes) | ✅ |
| 2 | Data layer (models, repositories, DataStore, sample) | ✅ |
| 3 | Core reusable components (15+ composables) | ✅ |
| 4 | Home screen (header, search, filters, list, settings popup) | ✅ |
| 5 | Player shell (header, bottom card, tab nav, drawer, capsule) | ✅ |
| 6 | Player tabs (Clean, Dialogue, Loops, Notes, Speed) | ✅ |
| 7 | Popups (Note, Loop, Dialogue slider, Translate stub) | ✅ |
| 8 | Audio engine (ExoPlayer, speed, gestures, loops) | ✅ |
| 9 | Polish (long-press, combined clickable, responsive frame) | ✅ |

---

## Build

This is a standard Android Studio project.

1. Open `/home/user/horizon-loop-android/` in **Android Studio Iguana** (or newer)
2. Android Studio will generate the Gradle wrapper (`gradlew`, `gradle-wrapper.jar`) — accept the prompt
3. Let it sync dependencies
4. Run on an emulator or device (minSdk 26, targetSdk 35)

**Requirements:** Android SDK 35, JDK 17, Kotlin 2.0+.

---

## Architecture

```
MVVM + Unidirectional State Flow

View (Compose)
  ↑ collectAsState
ViewModel (StateFlow<UiState>)
  ↑ calls
Repository
  ↑ manages
DataStore / In-Memory State / ExoPlayer
```

- **PlayerViewModel** is a shared singleton via Koin — its state persists when navigating Home ↔ Player.
- **HomeViewModel** and **PlayerViewModel** are injected via `koinViewModel()`.
- **AudioPlayerController** wraps Media3 ExoPlayer and exposes a `StateFlow<PlayerState>`.

---

## Project Structure (mirrors HTML/JS modules)

```
app/src/main/java/com/horizonloop/app/
├── HorizonLoopApp.kt              # Application + Koin init
├── MainActivity.kt
├── ui/
│   ├── AppRoot.kt                 # Switches Home ↔ Player
│   ├── container/PhoneContainer.kt  # 440×840 phone frame
│   ├── theme/                     # Color, Type, Shape, Spacing, Theme
│   ├── components/                # 17 reusable composables
│   ├── screens/
│   │   ├── home/                  # HomeScreen, ViewModel, State
│   │   └── player/                # PlayerScreen, ViewModel, State
│   │       └── tabs/              # CleanTab, DialogueTab, LoopsTab, NotesTab, SpeedTab
│   ├── popups/                    # NotePopup, LoopPopup, DialogueSliderPopup, TranslatePopup
│   └── util/                      # Animations, Format, Modifiers
├── data/
│   ├── model/                     # Audio, Note, Loop, Dialogue, Settings (AiEngine enum)
│   ├── local/SettingsDataStore.kt # DataStore Preferences
│   ├── repository/                # 5 repositories
│   └── sample/SampleData.kt       # 8 audios, 4 dialogues, 2 loops, 2 notes
├── audio/
│   └── AudioPlayerController.kt   # Media3 ExoPlayer wrapper
└── di/                            # AppModule, DataModule, ViewModelModule
```

---

## Tech Stack

- **Kotlin 2.0.21** + **Jetpack Compose BOM 2024.10.01**
- **Material 3** dark theme
- **MVVM** with `ViewModel` + `StateFlow`
- **Koin 3.5.6** for DI
- **DataStore Preferences** for settings persistence
- **Media3 ExoPlayer 1.4.1** for audio playback
- **Navigation between views** via shared `PlayerViewModel` state
- **Custom typography** (Inter + Noto Sans Bengali) — placeholder system fonts for now
- **Custom icons** via Material Icons Extended

---

## Feature Mapping (HTML → Compose)

| HTML Feature | Compose Location |
|---|---|
| Home audio list with search/filter | `HomeScreen.kt` + `HomeViewModel` |
| Audio card with long-press to pin | `AudioCard.kt` (combinedClickable) |
| Settings popup (API key + engine) | `HomeScreen.kt` (SettingsPopupView) |
| Player header (back/title/menu) | `PlayerHeader.kt` |
| Player bottom card (controls + meta) | `PlayerBottomCard.kt` |
| Progress bar (seekable) | `ProgressBar.kt` |
| 5 player tabs | `tabs/*.kt` |
| Nav drawer (bottom sheet) | `NavDrawer.kt` |
| Capsule menu (bottom sheet) | `CapsuleMenu.kt` |
| Note add/edit/delete | `NotesTab` + `NotePopup` |
| Loop add/edit/delete + preview | `LoopsTab` + `LoopPopup` + `PlayerViewModel.previewLoop` |
| Speed control (button + steppers) | `SpeedTab.kt` + nav drawer speed stepper |
| Audio mode (audio-only listening) | `PlayerViewModel.toggleAudioMode` + CleanTab hides text |
| Long-press left/right for speed | `CleanTab.kt` (pointerInput awaitEachGesture) |
| Translate (Whisper + reasoning) | `TranslatePopup.kt` UI stub + `PlayerViewModel.startTranslate` |
| Dialogue slider (slide-down) | `DialogueSliderPopup.kt` |
| Send popup | `SendPopup.kt` in components/Overlays |
| Phone frame on tablet (440×840) | `PhoneContainer.kt` (BoxWithConstraints) |

---

## Notes for Production

### Fonts
The HTML uses **Inter** and **Noto Sans Bengali**. To use the exact same fonts:
1. Download `.ttf` files from Google Fonts
2. Place in `app/src/main/res/font/`
3. Create `res/font/inter.xml` and `res/font/noto_sans_bengali.xml` font family XMLs
4. Update `Type.kt` to reference them via `Font(R.font.inter_regular, FontWeight.Normal)`
5. Add `<item name="android:fontFamily">@font/inter</item>` to themes.xml

For now, `Type.kt` uses `FontFamily.SansSerif` so the app builds without font files.

### Phosphor Icons
The HTML uses Phosphor Icons (regular weight). The Phosphor Compose library is included as a dependency (`com.adamglin:phosphor-android:1.0.0`). Currently using Material Icons Extended for common icons. To switch to Phosphor, replace icon references in components with the Phosphor equivalents.

### Translate API (Groq)
The translate UI is built (`TranslatePopup` + `SendPopup` + `capsule` menu entry). The `PlayerViewModel.startTranslate()` function currently shows a 1.5s mock "Sending..." then closes. To wire up Groq:
1. Add Retrofit + Groq API interface
2. Implement `TranslateUseCase` calling Whisper (voice→English) + reasoning model (English→Bangla)
3. Replace the `delay(1500)` in `startTranslate()` with the real call
4. Parse the response into `Dialogue` objects and update `DialogueRepository`

### Tests
No tests written. Recommended: add ViewModel unit tests + Compose UI tests for critical flows.

---

## Known Limitations

- **First-run:** You'll need to add Inter / Noto Sans Bengali fonts for exact typography match
- **Phosphor icons:** Currently using Material Icons — not pixel-perfect for the icon set
- **Backdrop blur:** Will fall back to a solid overlay color on devices below API 31
- **No offline audio caching:** Audio URLs stream directly from network
- **No tests:** Project is feature-complete but lacks test coverage
- **Single Activity:** All navigation via shared ViewModel state (no Navigation Compose for now)

---

## Next Steps

1. Open in Android Studio
2. Add the font files
3. Run on a device with API 26+ (Android 8+)
4. Test all flows: search, filter, play, note, loop, speed, translate
5. When ready, wire up the Groq API for translation
