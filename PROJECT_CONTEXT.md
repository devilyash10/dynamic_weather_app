# Dynamic Weather App - Project Context

## Overview
A modern, single-screen Android weather application built from scratch to demonstrate proficiency in modern Android development. Features a custom dark-themed, glassmorphic UI.

## Tech Stack
* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material 3)
* **Architecture:** MVVM (Clean Architecture approach)
* **Concurrency:** Coroutines & StateFlow
* **Networking:** Retrofit2 & Gson
* **Location:** FusedLocationProviderClient
* **Background Work:** WorkManager
* **Navigation:** Jetpack Navigation Compose
* **Local Storage:** DataStore (Preferences)

## Core Features
1.  Fetch and display 6+ weather metrics (temp, humidity, wind, sunrise/sunset, condition, UV, pressure).
2.  Automatic GPS-based location updates on startup.
3.  City search functionality with a saved "My Cities" list.
4.  Customizable background sync (15m, 30m, 1h).
5.  Settings configuration (Celsius/Fahrenheit, Display toggles).


## Project Roadmap & Status

* [x] **Phase 0:** Initial Project Configuration (Gradle dependencies, Manifest permissions).
* [x] **Phase 1:** UI/UX Finalization & Architecture Mapping (Detailed MVVM structure locked).
* [ ] **Phase 2:** Domain & Data Layer Setup (OneCall API 3.0 integration, DTOs, Repository interfaces).
* [ ] **Phase 3:** Location Services & Background Work (GPS, WorkManager).
* [ ] **Phase 4:** Core UI Components & Theme (Colors, Typography, Glassmorphic components).
* [ ] **Phase 5:** Home Screen Implementation (ViewModel, State integration, Layouts).
* [ ] **Phase 6:** Search & Settings Screens (DataStore integration, Navigation).
* [ ] **Phase 7:** Polish & Optimization (Cold start optimizations, animations).

## Current Working Focus
Currently executing **Phase 2**. Setting up the Retrofit interface using the OpenWeather One Call API 3.0 to fetch current, hourly, and daily data (including UV and pressure) in a single network request. Defining the Data Transfer Objects (DTOs).
