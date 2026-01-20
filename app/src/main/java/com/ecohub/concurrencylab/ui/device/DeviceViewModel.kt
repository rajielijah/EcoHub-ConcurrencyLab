package com.ecohub.concurrencylab.ui.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohub.concurrencylab.data.error.ConflictException
import com.ecohub.concurrencylab.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.ecohub.concurrencylab.ui.preferences.UiPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val repository: DeviceRepository,
    private val uiPreferences: UiPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DeviceUiState(
            collaborativeMode = uiPreferences.isCollaborativeModeEnabled()
        )
    )

    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<DeviceUiEffect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effects: SharedFlow<DeviceUiEffect> = _effects.asSharedFlow()


    init {
        observeDeviceState()
    }

    fun onIntent(intent: DeviceIntent) {
        when (intent) {
            is DeviceIntent.TemperatureInputChanged -> {
                _uiState.update { it.copy(temperatureInput = intent.value) }
            }

            is DeviceIntent.AdjustTemperature -> {
                adjustCurrentTemperature(intent.delta)
            }

            DeviceIntent.SetTemperatureClicked -> {
                submitTemperatureFromInput()
            }

            is DeviceIntent.CollaborativeModeToggled -> {
                _uiState.update { it.copy(collaborativeMode = intent.enabled) }
                uiPreferences.setCollaborativeModeEnabled(intent.enabled)
            }


            DeviceIntent.ConflictKeepTechnicianChosen -> {
                _uiState.update { it.copy(conflictDialog = null) }
            }

            DeviceIntent.ConflictForceOverwriteChosen -> {
                forceOverwriteFromConflict()
            }
        }
    }

    private fun observeDeviceState() {
        viewModelScope.launch {
            repository.deviceState.collect { deviceState ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        temperature = deviceState.temperature,
                        versionLabel = "v${deviceState.version}",
                        canIncrement = deviceState.temperature < repository.maxTemperature,
                        canDecrement = deviceState.temperature > repository.minTemperature,
                    )
                }
            }
        }
    }

    private fun submitTemperature(newTemp: Double) {
        viewModelScope.launch {
            val expectedVersion = repository.deviceState.value.version
            _uiState.update { it.copy(isUpdating = true) }
            try {
                repository.setTemperature(newTemp, expectedVersion)
                val actualTemp = repository.deviceState.value.temperature
                if (actualTemp != newTemp) {
                    emitSnackbar(
                        "Temperature adjusted to ${formatTemperature(actualTemp)}"
                    )
                } else {
                    emitSnackbar(
                        "Temperature updated to ${formatTemperature(actualTemp)}"
                    )
                }
            } catch (conflict: ConflictException) {
                handleConflict(conflict, newTemp)
            } finally {
                _uiState.update { it.copy(isUpdating = false) }
            }
        }
    }

    private fun submitTemperatureFromInput() {
        val parsed = _uiState.value.temperatureInput.trim().toDoubleOrNull()
        if (parsed == null) {
            emitSnackbar("Enter a valid temperature")
            return
        }
        submitTemperature(parsed)
    }

    private fun adjustCurrentTemperature(delta: Double) {
        val current = _uiState.value.temperature
            ?: error("Temperature must be available before adjusting")
        submitTemperature(current + delta)
    }



    private suspend fun handleConflict(
        conflict: ConflictException,
        userTemp: Double,
    ) {
        val currentState = _uiState.value
        if (currentState.collaborativeMode) {
            _uiState.update {
                it.copy(
                    temperatureInput = ""
                )
            }
            emitSnackbar(
                "Updated by technician to ${formatTemperature(conflict.latest.temperature)}"
            )
            return
        }

        _uiState.update {
            it.copy(
                conflictDialog = ConflictDialogState(
                    userAttemptedTemp = userTemp,
                    technicianTemp = conflict.latest.temperature,
                )
            )
        }
    }

    private fun forceOverwriteFromConflict() {
        val conflictSnapshot = _uiState.value.conflictDialog ?: return
        viewModelScope.launch {
            try {
                repository.forceUpdate(conflictSnapshot.userAttemptedTemp)
            } finally {
                _uiState.update { it.copy(conflictDialog = null) }
            }
        }
    }

    private fun emitSnackbar(message: String) {
        _effects.tryEmit(DeviceUiEffect.ShowSnackbar(message))
    }

    private fun formatTemperature(value: Double): String {
        return String.format("%.1f°C", value)
    }
}

