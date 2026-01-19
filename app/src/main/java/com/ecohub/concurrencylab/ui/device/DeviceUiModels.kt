package com.ecohub.concurrencylab.ui.device

data class DeviceUiState(
    val loading: Boolean = true,
    val temperatureText: String = "",
    val versionLabel: String = "",
    val temperatureInput: String = "",
    val collaborativeMode: Boolean = false,
    val isUpdating: Boolean = false,
    val conflictDialog: ConflictDialogState? = null
)

data class ConflictDialogState(
    val userAttemptedTemp: Double,
    val expectedVersion: Long,
    val technicianTemp: Double,
    val technicianVersion: Long
)

sealed class DeviceUiEffect {
    data class ShowSnackbar(val message: String) : DeviceUiEffect()
}

sealed class DeviceIntent {
    data class TemperatureInputChanged(val value: String) : DeviceIntent()
    data class SetTemperatureRequested(val newTemp: Double) : DeviceIntent()
    data class CollaborativeModeToggled(val enabled: Boolean) : DeviceIntent()
    data object ConflictKeepTechnicianChosen : DeviceIntent()
    data object ConflictForceOverwriteChosen : DeviceIntent()
}

