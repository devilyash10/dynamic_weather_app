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


# Dynamic Weather App - Project Context

## Roadmap & Status
* [x] **Phase 0:** Initial Project Configuration
* [x] **Phase 1:** UI/UX Finalization & Architecture Mapping
* [x] **Phase 2:** Domain & Data Layer Setup
* [x] **Phase 3:** Dependency Injection & Location Services
* [ ] **Phase 4:** Core UI Components & Material 3 Theme
* [ ] **Phase 5:** Home Screen Implementation
* [ ] **Phase 6:** Search & Settings Implementation
* [ ] **Phase 7:** Polish, Optimization & Release Prep

---

## Detailed Phase Documentation

### Phase 0: Initial Project Configuration
We established the project foundation using Kotlin DSL (build.gradle.kts). We integrated the Jetpack Compose Bill of Materials (BOM) to ensure library compatibility and added essential dependencies including Navigation Compose, Coroutines, Retrofit, FusedLocationProvider, and WorkManager. We also configured the Android Manifest to include permissions for internet access and location tracking. This phase ensured the project environment was stable and ready for architectural implementation.

### Phase 1: UI/UX Finalization & Architecture Mapping
We locked in the Material 3 UI/UX design, featuring a dark-themed aesthetic with glassmorphic cards and dynamic gradients, as seen in our design mockups. We mapped the entire application into a strict MVVM (Model-View-ViewModel) Clean Architecture folder structure. By strictly separating concerns into Data, Domain, and Presentation layers, we ensured that the codebase remains scalable, testable, and maintainable as we add features.

### Phase 2: Domain & Data Layer Setup
We configured the Retrofit network client to consume the OpenWeather One Call API 3.0, which provides efficient, consolidated weather data. We created specific Data Transfer Objects (DTOs) for robust JSON parsing. To isolate our business logic, we created pure Kotlin domain models (`WeatherInfo`) and a `WeatherRepository` interface. Finally, we implemented `WeatherRepositoryImpl` and utilized Kotlin extension functions in `WeatherMappers.kt` to safely transform raw network DTOs into UI-ready domain models, ensuring the Presentation layer remains decoupled from network implementation details.

### Phase 3: Dependency Injection & Location Services
We integrated Dagger Hilt as our Dependency Injection framework, starting with the `DynamicWeatherApp` application class and configuring `MainActivity` as the entry point. We established a clean abstraction for hardware services by defining a `LocationTracker` interface in the Domain layer. We then implemented `DefaultLocationTracker` in the Data layer using `FusedLocationProviderClient`, incorporating safe runtime permission checks to prevent crashes. Finally, we created `AppModule` to manage singleton instances of our Retrofit API, Location Services, and Repositories, ensuring our UI layer can easily and cleanly inject these dependencies.

---
## Current Working Focus
Moving into **Phase 4: Core UI Components & Material 3 Theme**. We will translate the UI design system into Jetpack Compose. This involves defining custom color palettes for our dark theme, configuring typography, and building highly reusable stateless components (like the `GlassCard` and `WeatherIcon`) that will act as the building blocks for our screens.
