package com.ecohub.concurrencylab.ui.device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeviceScreen(
    state: DeviceUiState,
    onIntent: (DeviceIntent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = Dimens.ScreenHorizontalPadding, vertical = Dimens.ScreenVerticalPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SectionSpacing)
        ) {
            ScreenHeader()
            TemperatureHeroCard(
                temperatureText = state.temperatureText,
                versionLabel = state.versionLabel,
                loading = state.loading,
                onAdjustClicked = { delta ->
                    onIntent(DeviceIntent.AdjustTemperature(delta))
                }
            )
            TemperatureControlsCard(
                temperatureInput = state.temperatureInput,
                loading = state.loading,
                isUpdating = state.isUpdating,
                onTemperatureInputChanged = { onIntent(DeviceIntent.TemperatureInputChanged(it)) },
                onUpdateClicked = { onIntent(DeviceIntent.SetTemperatureClicked) }
            )
            CollaborativeModeCard(
                enabled = state.collaborativeMode,
                onEnabledChanged = { onIntent(DeviceIntent.CollaborativeModeToggled(it)) }
            )
        }
    }

    val conflict = state.conflictDialog
    if (conflict != null) {
        ConflictDialog(
            state = conflict,
            onKeepTechnician = { onIntent(DeviceIntent.ConflictKeepTechnicianChosen) },
            onOverwrite = { onIntent(DeviceIntent.ConflictForceOverwriteChosen) }
        )
    }
}

@Composable
private fun ScreenHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.TextTightSpacing)) {
        Text(
            text = "Device Control",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Set a target temperature and handle technician updates.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TemperatureHeroCard(
    temperatureText: String,
    versionLabel: String,
    loading: Boolean,
    onAdjustClicked: (Double) -> Unit
) {
    val isAtMax = temperatureText.startsWith("30.0")
    val isAtMin = temperatureText.startsWith("5.0")
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.CardPaddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimens.TextSpacing)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Current temperature",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!loading) {
                    Text(
                        text = versionLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription =
                            if (loading)
                                "Current temperature is loading"
                            else
                                "Current temperature: $temperatureText"
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { onAdjustClicked(-0.5) },
                    enabled = !loading  && !isAtMin
                ) {
                    Text("–")
                }

                Text(
                    text = if (loading) "—" else temperatureText,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    onClick = { onAdjustClicked(+0.5) },
                    enabled = !loading && !isAtMax
                ) {
                    Text("+")
                }
            }
        }
    }
}

@Composable
private fun TemperatureControlsCard(
    temperatureInput: String,
    loading: Boolean,
    isUpdating: Boolean,
    onTemperatureInputChanged: (String) -> Unit,
    onUpdateClicked: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.CardPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.ControlSpacing)
        ) {
            Text(
                text = "Set temperature",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = temperatureInput,
                onValueChange = onTemperatureInputChanged,
                label = { Text("Temperature") },
                supportingText = { Text("Example: 21.5") },
                singleLine = true,
                enabled = !loading && !isUpdating,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Temperature input" }
            )

            Button(
                onClick = onUpdateClicked,
                enabled = !loading && !isUpdating,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.PrimaryButtonHeight),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = if (isUpdating) "Updating…" else "Update")
            }
        }
    }
}

@Composable
private fun CollaborativeModeCard(
    enabled: Boolean,
    onEnabledChanged: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.CardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.TextTightSpacing)
            ) {
                Text(
                    text = "Collaborative Mode",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = if (enabled) {
                        "Conflicts auto-resolve by keeping the technician value."
                    } else {
                        "If there’s a conflict, you’ll choose what to keep."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.padding(start = Dimens.ControlSpacing))
            Switch(
                checked = enabled,
                onCheckedChange = onEnabledChanged,
                modifier = Modifier.semantics { contentDescription = "Collaborative mode" }
            )
        }
    }
}

@Composable
private fun ConflictDialog(
    state: ConflictDialogState,
    onKeepTechnician: () -> Unit,
    onOverwrite: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Update conflict") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.TextSpacing)) {
                Text(
                    text = "A technician updated this device while you were editing.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Technician: ${formatDialogTemp(state.technicianTemp)}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Your value: ${formatDialogTemp(state.userAttemptedTemp)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onKeepTechnician) {
                Text("Keep technician")
            }
        },
        dismissButton = {
            TextButton(onClick = onOverwrite) {
                Text("Use mine")
            }
        }
    )
}

private fun formatDialogTemp(value: Double): String {
    return String.format("%.1f°C", value)
}

private object Dimens {
    val ScreenHorizontalPadding = 16.dp
    val ScreenVerticalPadding = 20.dp
    val SectionSpacing = 20.dp

    val CardPadding = 16.dp
    val CardPaddingLarge = 20.dp

    val TextTightSpacing = 4.dp
    val TextSpacing = 12.dp
    val ControlSpacing = 12.dp

    val PrimaryButtonHeight = 48.dp
}
