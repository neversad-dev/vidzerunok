![logo](./docs/logo.png)

[![GitHub issues](https://img.shields.io/github/issues/vshymanskyy/StandWithUkraine.svg)](https://github.com/vshymanskyy/StandWithUkraine/issues)
[![StandWithUkraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://github.com/vshymanskyy/StandWithUkraine/blob/main/docs/README.md)

# 🎞️ Vidzerunok

**Vidzerunok** is a cross-platform video frame annotation tool built with **Kotlin Multiplatform** and **Compose Multiplatform**. It lets you extract a frame from a video, enhance it visually, annotate it with simple elements, and export or share your result.

> ✨ Inspired by the Ukrainian word _“візерунок”_ meaning “pattern” or “ornament,” **Vidzerunok** blends creativity and precision into a clean visual editing experience.

---

## 🚀 Features

- 📽️ Upload video files (MP4, MOV, etc.)
- 🎯 Select and extract a specific frame
- 🖼️ Edit the frame with:
  - Zoom, contrast, brightness
  - Add rectangle, circle, arrow, or text elements
- 💾 Export as image or share directly

---

## 🛠 Tech Stack

| Layer             | Tech                                         |
|------------------|----------------------------------------------|
| Language          | Kotlin Multiplatform (KMP)                   |
| UI                | JetBrains Compose Multiplatform              |
| Video Processing  | FFmpeg (via platform-specific bindings)      |
| State Management  | MVVM + Kotlin Flows                          |

---

## 🧭 Roadmap

- [ ] Project structure with shared modules
- [ ] Frame extraction prototype (Desktop)
- [ ] Basic shape drawing + export
- [ ] Image filters (brightness, contrast, zoom)
- [ ] Project saving/loading
- [ ] Android support
- [ ] AI: background remover & auto-highlight
- [ ] iOS support (long-term)

---

## 📸 Preview

> ![Vidzerunok Preview](docs/demo.gif)

---

## 📦 Getting Started

### 🔧 Prerequisites

- JDK 17+
- Android Studio or IntelliJ IDEA
- Kotlin Multiplatform plugin

### 💻 Run Locally

```bash
git clone https://github.com/yourusername/vidzerunok.git
cd vidzerunok
./gradlew run
```

## 🧾 License

This project is licensed under the [MIT License](LICENSE).


---
