# 🧠💀 Brainrot Overload

## A Chaotic Java GUI Experience

!['Brainrot Overload'](https://github.com/joshuaparker2/Brainrot/blob/brainrot/assets/backrounds/brainrot.png?raw=true)

### Overview

Brainrot Overload is a disruptive Java application designed to simulate sensory overload through erratic GUI behavior, chaotic meme bombardment, and glitchy audio effects. This project serves as an experimental test of Java Swing limitations and user experience under extreme conditions.

⚠️ **Disclaimer:** This application is developed for educational and research purposes. It does not cause permanent system changes and should be tested in a controlled environment (e.g., a virtual machine).

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
   - [Uncontrollable UI](#uncontrollable-ui)
   - [Meme Chaos Engine](#meme-chaos-engine)
   - [Audio Assault](#audio-assault)
   - [Persistent Pop-Ups](#persistent-pop-ups)
3. [Installation](#installation)
   - [Requirements](#requirements)
   - [Execution Methods](#execution-methods)
4. [Emergency Killswitch](#emergency-killswitch)
5. [Logging System](#logging-system)
6. [Technical Breakdown](#technical-breakdown)
7. [Diagrams and flowcharts](#diagrams-and-flowcharts)
8. [Ethical Notice](#ethical-notice)
9. [Warnings](#warnings)

---

### Features

#### Uncontrollable UI

- Borderless full-screen background (`CorruptedBackgroundWindow`)
- Pop-ups that:
  - Move erratically (`BrainrotPopUpWindow`)
  - Appear in random sizes and positions
  - Disable the mouse cursor visibility

#### Meme Chaos Engine

- Loads images dynamically from JAR resources (`MemeManager`)
- Supports .jpg and .png formats (stored in `brainrot/assets/memes`)
- Displays fallback text if images are unavailable

#### Audio Assault

- Loops WAV audio (`brainrot/assets/brainrot_noise.wav`)
- Handles audio playback errors gracefully

#### Persistent Pop-Ups

- Automatic window spawning (new pop-up every 100ms)
- Windows-only components:
  - Executable wrapper (`brainrot.exe`, created with Launch4j)

### Installation

#### Requirements

- Java 8+ Runtime (for running the JAR)
- Windows OS (for EXE version)

#### Execution Methods

- **JAR File**
  ```bash
  java -jar brainrot.jar

### EXE (Windows)
Simply double-click `brainrot.exe`

### Emergency Killswitch

Press **CTRL + SHIFT + X** to:

- Stop all running timers
- Close all open windows
- Exit the application
- Display a restoration message

### Logging System

All pop-up creations are logged in `log.txt`:


### Technical Breakdown

#### Java Components

| Class                     | Purpose                      |
| ------------------------- | ---------------------------- |
| `BrainrotVirus`            | Main controller (timers/audio)|
| `BrainrotPopUpWindow`      | Erratic meme pop-ups         |
| `CorruptedBackgroundWindow`| Full-screen chaotic overlay  |
| `MemeManager`              | Meme image loader            |

## Diagrams and flowcharts

-- Main --

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
                                     
-- Erratic Motion Flow --

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
-- Key Event Dispatcher --

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


### Ethical Notice

**For Educational & Research Use Only!**

- ✅ No permanent system changes
- ✅ All effects terminate on killswitch or reboot
- ✅ Logs are session-specific (not persistent)

Developed to study:

- Java Swing/AWT limitations
- UX response to chaotic interfaces
- Controlled software-induced disruptions

⚠️ **WARNING:** This application may cause temporary frustration. Not recommended for epileptic users. Test in a virtual machine for safety.
