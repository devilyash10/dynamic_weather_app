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
* [x] **Phase 4:** Core UI Components & Material 3 Theme
* [x] **Phase 5:** Home Screen Implementation
* [x] **Phase 6:** Search & Settings Implementation
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

### Phase 4: Core UI Components & Material 3 Theme
We translated the UI design system into Jetpack Compose, establishing a premium, unified dark theme. We defined specific color palettes (`NimbusDark`, `NimbusGlass`) and custom typography in our theme files. We built a highly reusable `GlassCard` composable to serve as the foundation for our translucent UI containers. Finally, we set up the navigation structure using a type-safe `Screen` sealed class, implemented the `CustomBottomNavBar`, and created a utility to map API weather codes to local image assets.
---
### Note On HomeViewModel
Encapsulation: We use a private _state (MutableStateFlow) and expose a public state (StateFlow). This enforces a unidirectional data flow—the UI can only read the state, never write to it directly.
The .fold function: This is a very elegant Kotlin standard library feature for handling the Result class we created in our Repository. It cleanly forces you to handle both the onSuccess and onFailure paths.

### Phase 5: Home Screen Implementation
We brought the application to life by implementing the `HomeScreen` and its governing `HomeViewModel`. We utilized the `StateFlow` pattern to manage Loading, Success, and Error states in a unidirectional data flow. The UI was constructed modularly to perfectly match the mockups, assembling custom components including the `LocationHeader`, a massive `MainTemperatureDisplay`, an optimized, horizontally scrolling `HourlyForecastRow`, a 2x2 `WeatherMetricsGrid` (detailing humidity, wind, UV, and pressure), and a vertical `DailyForecastSection`. This phase successfully bridged our pure Kotlin business logic with our Material 3 Compose UI layer.

### Phase 6: Search & Settings Implementation
We scaled out full persistence architectures across the application layer. We integrated Jetpack DataStore to capture localized configuration preferences, constructing full mathematical inversion bridges to allow real-time toggling between Celsius and Fahrenheit parameters cleanly across all screen metrics. We decoupled from OpenWeather API dependencies to transition to Open-Meteo's subscription-free tiers. Finally, we added an Android Room SQLite Database ecosystem to handle localized storage parameters for custom user locations, tracking insertions, deletions, and continuous flow bindings inside our debounced `SearchScreen` workflow module.

---

## Current Working Focus
Moving into **Phase 7: Polish, Optimization & Release Prep**. The final goal is to connect the database to the Home Screen so clicking a saved city switches the home weather view, build out empty states, ensure dark-mode status bar formatting, run performance tracing, and polish transitions.