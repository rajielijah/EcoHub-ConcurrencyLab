package com.ecohub.concurrencylab.ui.device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ecohub.concurrencylab.R

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
                temperature = state.temperature,
                versionLabel = state.versionLabel,
                loading = state.loading,
                canIncrement = state.canIncrement,
                canDecrement = state.canDecrement,
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
                enabledInteraction = state.conflictDialog == null,
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
            text = stringResource(R.string.device_control_title),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.device_control_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TemperatureHeroCard(
    temperature: Double?,
    versionLabel: String,
    loading: Boolean,
    canIncrement: Boolean,
    canDecrement: Boolean,
    onAdjustClicked: (Double) -> Unit
) {

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
                    text = stringResource(R.string.current_temperature),
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
                            if (loading) {
                                "Current temperature is loading"
                            } else {
                                "Current temperature is ${formatTemp(temperature)}"
                            }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { onAdjustClicked(-0.5) },
                    enabled = !loading && canDecrement,
                    modifier = Modifier.semantics {
                        contentDescription = "Decrease temperature"
                    }
                ) {
                    Text("–")
                }

                Text(
                    text = if (loading || temperature == null) "—" else formatTemp(temperature),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    onClick = { onAdjustClicked(+0.5) },
                    enabled = !loading && canIncrement,
                    modifier = Modifier.semantics {
                        contentDescription = "Increase temperature"
                    }
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
                text = stringResource(R.string.set_temperature),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = temperatureInput,
                onValueChange = onTemperatureInputChanged,
                label = { Text(stringResource(R.string.temperature_input_label)) },
                supportingText = { Text(stringResource(R.string.temperature_input_hint)) },
                singleLine = true,
                enabled = !loading && !isUpdating,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
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
                Text(
                    text = if (isUpdating)
                        stringResource(R.string.updating)
                    else
                        stringResource(R.string.update)
                )
            }
        }
    }
}

@Composable
private fun CollaborativeModeCard(
    enabled: Boolean,
    enabledInteraction: Boolean,
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
                    text = stringResource(R.string.collaborative_mode))
                Text(
                    text = if (enabled) {
                        stringResource(R.string.collaborative_on)
                    } else {
                        stringResource(R.string.collaborative_off)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.padding(start = Dimens.ControlSpacing))
            Switch(
                checked = enabled,
                onCheckedChange = {
                    if (enabledInteraction) onEnabledChanged(it)
                },
                enabled = enabledInteraction,
                modifier = Modifier.semantics {
                    contentDescription = "Collaborative mode"
                }
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
        title = { Text(stringResource(R.string.conflict_title))  },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.TextSpacing)) {
                Text(
                    text = stringResource(R.string.conflict_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Technician: ${formatTemp(state.technicianTemp)}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Your value: ${formatTemp(state.userAttemptedTemp)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onKeepTechnician) {
                Text(stringResource(R.string.keep_technician))
            }
        },
        dismissButton = {
            TextButton(onClick = onOverwrite) {
                Text(stringResource(R.string.use_mine))
            }
        }
    )
}

private fun formatTemp(value: Double?): String =
    value?.let { String.format("%.1f°C", it) } ?: "—"
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
