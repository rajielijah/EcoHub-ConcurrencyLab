package com.ecohub.concurrencylab.ui.device

data class DeviceUiState(
    val loading: Boolean = true,
    val temperature: Double? = null,
    val versionLabel: String = "",
    val temperatureInput: String = "",
    val collaborativeMode: Boolean = false,
    val isUpdating: Boolean = false,
    val conflictDialog: ConflictDialogState? = null,
    val canIncrement: Boolean = false,
    val canDecrement: Boolean = false,
) {

    val temperatureText: String
        get() = temperature?.let { String.format("%.1f°C", it) } ?: "—"
}

data class ConflictDialogState(
    val userAttemptedTemp: Double,
    val technicianTemp: Double,
)

sealed class DeviceUiEffect {
    data class ShowSnackbar(val message: String) : DeviceUiEffect()
}

sealed class DeviceIntent {
    data class TemperatureInputChanged(val value: String) : DeviceIntent()

    data class AdjustTemperature(val delta: Double) : DeviceIntent()
    data object SetTemperatureClicked : DeviceIntent()
    data class CollaborativeModeToggled(val enabled: Boolean) : DeviceIntent()
    data object ConflictKeepTechnicianChosen : DeviceIntent()
    data object ConflictForceOverwriteChosen : DeviceIntent()
}

