# EcoHub Device Control Panel

This is an Android application that allows homeowners to monitor and control their installed Heat Pumps. 
It demonstrates thread-safe device state management with optimistic concurrency control and collaborative conflict resolution.

## Setup Instructions

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK with API level 24+ (Android 7.0+)

### Build and Run

1. **Clone the repository** : Open the project in Android Studio

2. **Sync Gradle**:

3. **Run the app**:
   - Connect an Android device (API 24+) or start an emulator
   - Click "Run" in Android Studio,

4. **Run tests**:


## Architecture Decisions

### Key Principles

1. **Single Source of Truth**: `FakeDeviceRepository` owns and manages the device state.
2. **Immutable State**: `DeviceState` is a data class; all mutations create new instances.
3. **Unidirectional Data Flow**: UI → ViewModel → Repository → StateFlow → ViewModel → UI.
4. **Separation of Concerns**: Business logic lives in ViewModel; UI is purely declarative.

## Concurrency Strategy

### Thread Safety

To avoid conflicts, only one update can change the device state at a time. This is handled in `FakeDeviceRepository`.


### Optimistic Concurrency Control

Instead of blocking reads, the app uses optimistic locking:

- Every update increments version
- User updates include the version they last observed.
- If versions mismatch, `ConflictException` is thrown with the latest state.
- This prevents lost updates without pessimistic locking.

### Background Technician Job

A coroutine runs every 15 seconds, updating temperature and incrementing version.

The technician job uses the same `mutex.withLock` critical section, ensuring deterministic interleaving.

### Manual temperature input

In addition to the + / − buttons, I added a text input field for setting the temperature directly.
The buttons adjust the temperature in fixed steps of 0.5, which is convenient for small changes, but it doesn’t cover all valid values. The text field allows users to enter any supported temperature

### Making race conditions observable (artificial latency)

In a local environment, race conditions are rare because everything executes almost instantly.

To make concurrency behavior observable and testable, I introduced artificial latency in the repository’s user update path:

- The delay simulates slow network or remote processing time
- It creates a real window where the technician can update first
- Version mismatches then occur naturally

Latency is:

- Only in the data layer
- Used only to demonstrate concurrency behavior


### Coroutine Scope Ownership

- **Repository**: Owns `CoroutineScope(SupervisorJob() + Dispatchers.Default)` for the technician job.
- **ViewModel**: Uses `viewModelScope` for collecting flows and calling repository methods.
- **UI**: Uses Compose lifecycle-aware collection; no long-lived jobs in Composables.


## Trade-offs

### Mutex vs Single-Threaded Dispatcher

Mutex was chosen for explicit critical sections and easier reasoning.

**Trade-off**: Slightly more verbose, but clearer ownership and easier to extend.

### Intent-driven MVVM (MVI-style flow)

This implementation uses MVVM with explicit intents and unidirectional data flow, rather than a strict MVI setup.

I chose this approach because it keeps the code easy to read and practical, while still giving me a clear and predictable flow of events. 
Every user action goes through an intent, state is updated in one place, and the UI simply reacts to that state. 
This was especially useful for handling concurrent updates and conflicts without spreading logic across the UI.

### Repository-enforced constraints

Prevents invalid state regardless of how updates are triggered.

### Artificial latency

Makes race conditions observable without polluting UI logic.v

### String resources

All user-facing UI text is defined in strings.xml.
Snackbar messages are generated in the ViewModel as one-off UI effects, 
which keeps the ViewModel free of Android resource dependencies while ensuring that the main UI text remains fully localizable. I
n a production app, these messages could also be moved to string resources if more comprehensive localization were required.

### FakeDeviceRepository

FakeDeviceRepository embeds domain rules (min/max temperature, technician interval)directly to keep the concurrency model self-contained and observable.
In production, these would be provided by domain services or configuration.

### In-Memory setup

- I chose a simple in-memory setup instead of persistence or networking.
This means the app resets when it’s closed, but it keeps the code easy to read and makes the concurrency behavior very clear.


## AI Tool Usage

Tool: ChatGPT.

 ### Why/How it was used

- It helped me think through how I could simulate update conflicts in a the app.

- I also used it for tests. When a test failed in a way that wasn’t obvious, I pasted the error and used ChatGPT to quickly understand what was going wrong and how to fix it.

- It was also useful during documentation (README.md).

**ChatGPT did not design the architecture or write the core logic. It was mainly used as a support tool for debugging, validation, and documentation.**