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

class DeviceViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeviceUiState())
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<DeviceUiEffect>(extraBufferCapacity = 1)
    val effects: SharedFlow<DeviceUiEffect> = _effects.asSharedFlow()

    init {
        observeDeviceState()
    }

    fun onIntent(intent: DeviceIntent) {
        when (intent) {
            is DeviceIntent.TemperatureInputChanged -> {
                _uiState.update { it.copy(temperatureInput = intent.value) }
            }

            DeviceIntent.SetTemperatureClicked -> {
                submitTemperatureFromInput()
            }

            is DeviceIntent.CollaborativeModeToggled -> {
                _uiState.update { it.copy(collaborativeMode = intent.enabled) }
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
                        temperatureText = formatTemperature(deviceState.temperature),
                        versionLabel = "v${deviceState.version}"
                    )
                }
            }
        }
    }

    private fun submitTemperature(newTemp: Double) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdating = true) }
            val expectedVersion = repository.deviceState.value.version
            try {
                repository.setTemperature(newTemp, expectedVersion)
            } catch (conflict: ConflictException) {
                handleConflict(conflict, newTemp, expectedVersion)
            } finally {
                _uiState.update { it.copy(isUpdating = false) }
            }
        }
    }

    private fun submitTemperatureFromInput() {
        val parsed = _uiState.value.temperatureInput.trim().toDoubleOrNull()
        if (parsed == null) {
            _effects.tryEmit(DeviceUiEffect.ShowSnackbar("Enter a valid temperature"))
            return
        }
        submitTemperature(parsed)
    }

    private suspend fun handleConflict(
        conflict: ConflictException,
        userTemp: Double,
        expectedVersion: Long
    ) {
        val currentState = _uiState.value
        if (currentState.collaborativeMode) {
            _effects.tryEmit(
                DeviceUiEffect.ShowSnackbar(
                    message = "Technician updated temperature to ${formatTemperature(conflict.latest.temperature)}"
                )
            )
            return
        }

        _uiState.update {
            it.copy(
                conflictDialog = ConflictDialogState(
                    userAttemptedTemp = userTemp,
                    expectedVersion = expectedVersion,
                    technicianTemp = conflict.latest.temperature,
                    technicianVersion = conflict.latest.version
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

    private fun formatTemperature(value: Double): String {
        return String.format("%.1f°C", value)
    }
}

