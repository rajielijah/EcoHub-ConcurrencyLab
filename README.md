# EcoHub Device Control Panel

Android application demonstrating thread-safe device state management with optimistic concurrency control and collaborative conflict resolution.

## Architecture


### Key Principles

1. **Single Source of Truth**: `FakeDeviceRepository` owns the canonical `MutableStateFlow<DeviceState>`.
2. **Immutable State**: `DeviceState` is a data class; all mutations create new instances.
3. **Unidirectional Data Flow**: UI → ViewModel → Repository → StateFlow → ViewModel → UI.
4. **Separation of Concerns**: Business logic lives in ViewModel; UI is purely declarative.

## Concurrency Strategy

### Thread Safety

All mutations to `DeviceState` are serialized through a single `Mutex` in `FakeDeviceRepository`:

```kotlin
private val mutex = Mutex()

override suspend fun setTemperature(newTemp: Double, expectedVersion: Long) {
    mutex.withLock {
        val current = _deviceState.value
        if (current.version != expectedVersion) {
            throw ConflictException(current)
        }
        _deviceState.value = current.copy(temperature = newTemp, version = current.version + 1)
    }
}
```

### Optimistic Concurrency Control

- Each state update increments `version`.
- User updates include an `expectedVersion` from a snapshot.
- If versions mismatch, `ConflictException` is thrown with the latest state.
- This prevents lost updates without pessimistic locking.

### Background Technician Job

A coroutine runs every 15 seconds, updating temperature and incrementing version:

```kotlin
private val technicianJob: Job = scope.launch {
    while (isActive) {
        delay(technicianIntervalMillis)
        advanceTechnicianReading()
    }
}
```

The technician job uses the same `mutex.withLock` critical section, ensuring deterministic interleaving.

### Coroutine Scope Ownership

- **Repository**: Owns `CoroutineScope(SupervisorJob() + Dispatchers.Default)` for the technician job.
- **ViewModel**: Uses `viewModelScope` for collecting flows and calling repository methods.
- **UI**: Uses Compose lifecycle-aware collection; no long-lived jobs in Composables.

## Conflict Resolution

### Collaborative Mode (ON)

When enabled and a conflict occurs:
- Technician value is accepted automatically.
- User's pending change is discarded.
- One-time snackbar effect informs the user.

### Collaborative Mode (OFF)

When disabled and a conflict occurs:
- `ConflictDialogState` is set in UI state.
- Dialog presents both values.
- User chooses: "Keep technician" or "Use mine" (force update).

## Trade-offs

### Mutex vs Single-Threaded Dispatcher

**Chosen**: `Mutex` around `MutableStateFlow` on `Dispatchers.Default`.

**Rationale**: Explicit critical sections are easier to reason about and test. Single-threaded dispatchers serialize access but are heavier and less explicit for small apps.

**Trade-off**: Slightly more verbose, but clearer ownership and easier to extend.

### Optimistic vs Pessimistic Locking

**Chosen**: Optimistic concurrency with version numbers.

**Rationale**: Matches REST/ETag patterns, allows concurrent reads, and avoids deadlock risk from held locks.

**Trade-off**: Users may see conflicts, but they're explicit and handled via UI state or auto-resolved.

### Collaborative Mode Policy

**Chosen**: "Technician wins" when collaborative mode is ON.

**Rationale**: Simple rule, avoids complex merging logic, and matches common conflict resolution patterns.

**Trade-off**: User's change is discarded. Alternative approaches could show both values side-by-side or auto-retry with new version.

### ViewModel-First Error Handling

**Chosen**: All `ConflictException` handling lives in ViewModel; UI never catches domain exceptions.

**Rationale**: Clear separation of concerns, testable, and explicit error semantics via UI state.

**Trade-off**: More boilerplate (UI state + effect modeling), but much clearer error handling.


## Dependencies

- Kotlin Coroutines + Flow
- Jetpack Compose
- ViewModel (lifecycle-viewmodel-ktx)
- Material 3

## AI Tool Usage Log

Tool: Cursor, used sparingly.  
Why: Speed up UI polish wording and minor refactors; tighten documentation phrasing.  
Where: Adjusted Compose layout copy/spacing and refined README text. Core architecture and concurrency logic were implemented manually.
