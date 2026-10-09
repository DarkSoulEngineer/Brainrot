# Brainrot
**A chaotic Java graphical experience — Brainrot Overload**

[![License](https://img.shields.io/github/license/DarkSoulEngineer/Brainrot)](LICENSE)
![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?logo=openjdk&logoColor=white)

![Brainrot Overload running](https://github.com/user-attachments/assets/070eed6f-8a26-4730-a64c-011ee4575056)

## Table of Contents

- [Description](#description)
- [Ethical Notice](#ethical-notice)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Logging System](#logging-system)
- [Technical Breakdown](#technical-breakdown)
- [Diagrams](#diagrams)
- [License](#license)

## Description

Brainrot Overload is a disruptive Java application designed to simulate sensory overload through erratic GUI behavior, chaotic meme bombardment, and glitchy audio effects. This project serves as an experimental test of Java Swing limitations and user experience under extreme conditions.

> **Disclaimer:** This application is developed for educational and research purposes. It does not cause permanent system changes and should be tested in a controlled environment (e.g., a virtual machine).

## Ethical Notice

**For educational and research use only.**

- No permanent system changes
- All effects terminate on the killswitch or system reboot
- Logs are session-specific (not persistent)

Developed to study:

- Java Swing/AWT limitations
- UX response to chaotic interfaces
- Controlled software-induced disruptions

**Warning:** This application may cause temporary frustration. Not recommended for epileptic users. Test in a virtual machine for safety.

## Features

### Uncontrollable UI

- Borderless full-screen background (`CorruptedBackgroundWindow`)
- Pop-ups that:
  - Move erratically (`BrainrotPopUpWindow`)
  - Appear in random sizes and positions
  - Disable the mouse cursor visibility

### Meme Chaos Engine

- Loads images dynamically from JAR resources (`MemeManager`)
- Supports .jpg and .png formats (stored in `brainrot/assets/memes`)
- Displays fallback text if images are unavailable

### Audio Assault

- Loops WAV audio (`brainrot/assets/brainrot_noise.wav`)
- Handles audio playback errors gracefully

### Persistent Pop-Ups

- Automatic window spawning (new pop-up every 100ms)
- Windows-only components:
  - Executable wrapper (`brainrot.exe`, created with Launch4j)

## Requirements

- Java 8+ runtime (for running the JAR)
- Windows OS (for the EXE version)

## Installation

Clone the repository:

```bash
git clone https://github.com/DarkSoulEngineer/Brainrot.git
cd Brainrot
```

### Execution Methods

- **JAR file** (from the repository root):

  ```bash
  java -jar BrainrotVirus.jar
  ```

### EXE (Windows)

Simply double-click `virus/brainrot.exe`.

## Usage

Launch the application with one of the execution methods above. The background window, music, and pop-up timers start immediately.

### Emergency Killswitch

Press **CTRL + SHIFT + X** to:

- Stop all running timers
- Close all open windows
- Exit the application
- Display a restoration message

## Logging System

All pop-up creations are logged to `log.txt`.

## Technical Breakdown

### Java Components

| Class                      | Purpose                       |
| -------------------------- | ----------------------------- |
| `BrainrotVirus`            | Main controller (timers/audio) |
| `BrainrotPopUpWindow`      | Erratic meme pop-ups          |
| `CorruptedBackgroundWindow`| Full-screen chaotic overlay   |
| `MemeManager`              | Meme image loader             |

## Diagrams

### Main

```text
                              ┌─────────────────────────┐
                              │ Program Initialization  │
                              └──────────┬──────────────┘
                                          │
                                          ▼
                              ┌────────────────────────────┐
                              │ Launch Background Window   │
                              ├────────────────────────────┤
                              │ Start Background Music     │
                              ├────────────────────────────┤
                              │ Set Up Key Event Dispatcher│
                              ├────────────────────────────┤
                              │ Begin Pop-Up Timer         │
                              └───────────┬────────────────┘
                                          │
                       ┌──────────────────┴─────────────────┐
                       ▼                                    ▼
         ┌───────────────────────────┐  ┌───────────────────────────┐
         │ Load and Play Music       │  │ Create New Pop-Up Window  │
         └─────────────┬─────────────┘  └───────────────┬───────────┘
                       │                                │
                       ▼                                ▼
            ┌────────────────────────┐       ┌─────────────────────────┐
            │ Handle Audio Errors    │       │ Load Meme Images        │
            └────────────────────────┘       ├─────────────────────────┤
                                             │ Configure Window        │
                                             ├─────────────────────────┤
                                             │ Start Erratic Motion    │
                                             └─────────────────────────┘
```

### Erratic Motion Flow

```text
                           ┌──────────────────────────────┐
                           │ Start Erratic Motion Timer   │
                           └───────────────┬──────────────┘
                                           │
                                           ▼
                              ┌─────────────────────────┐
                              │ Move Window Randomly    │
                              ├─────────────────────────┤
                              │ Bounce Off Edges        │
                              ├─────────────────────────┤
                              │ Random Speed Change     │
                              └─────────────────────────┘
```

### Key Event Dispatcher

```text
                           ┌───────────────────────────────┐
                           │ Key Event Dispatcher Checks   │
                           └───────────────┬───────────────┘
                                           ▼
                              ┌───────────────────────────┐
                              │ Ctrl + Shift + X Pressed? │
                              ├───────────────────────────┤
                              │         Yes               │
                              └───────────┬───────────────┘
                                          ▼
                           ┌──────────────────────────────┐
                           │ Terminate Virus Infection    │
                           ├──────────────────────────────┤
                           │ Stop Timers and Clear Pop-Ups│
                           ├──────────────────────────────┤
                           │ Show Message and Exit        │
                           └──────────────────────────────┘
```

## License

Apache-2.0 — see [LICENSE](LICENSE).
