#!/bin/bash
# build.sh — Fast incremental builds for Horizon Loop
# Usage:
#   ./build.sh           → Build debug APK (incremental, ~10-15s after first run)
#   ./build.sh check     → Compile Kotlin only, no APK (~10s, fastest feedback)
#   ./build.sh clean     → Wipe build cache and rebuild from scratch
#   ./build.sh install   → Build + push to connected adb device

set -e

export JAVA_HOME=/nix/store/h32r2d86515rimvyf8ih0dpwiczd5gj5-openjdk-17.0.7+7
export ANDROID_HOME=/nix/store/nibdn1wppjp3gqw1z3y14s291r8r9rhn-androidsdk/libexec/android-sdk
export ANDROID_SDK_ROOT=$ANDROID_HOME
export PATH=$JAVA_HOME/bin:$PATH

cd "$(dirname "$0")"

MODE="${1:-apk}"

case "$MODE" in
  check)
    echo "⚡ Compiling Kotlin only (no dex/package/apk)..."
    ./gradlew :app:compileDebugKotlin --offline
    ;;
  clean)
    echo "🧹 Wiping build cache..."
    ./gradlew clean
    echo "🔨 Full rebuild from scratch..."
    ./gradlew :app:assembleDebug
    ;;
  install)
    echo "🔨 Building APK..."
    ./gradlew :app:assembleDebug
    APK="app/build/outputs/apk/debug/app-debug.apk"
    if [ -f "$APK" ]; then
      echo "📲 Installing to device..."
      adb install -r "$APK"
    else
      echo "❌ APK not found at $APK"
      exit 1
    fi
    ;;
  apk|*)
    echo "🔨 Building debug APK (incremental)..."
    ./gradlew :app:assembleDebug --offline
    APK="app/build/outputs/apk/debug/app-debug.apk"
    if [ -f "$APK" ]; then
      echo ""
      echo "✅ APK ready: $APK ($(du -h "$APK" | cut -f1))"
    fi
    ;;
esac
