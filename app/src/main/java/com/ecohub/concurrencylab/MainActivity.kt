package com.ecohub.concurrencylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.ecohub.concurrencylab.ui.device.DeviceScreen
import com.ecohub.concurrencylab.ui.device.DeviceUiEffect
import com.ecohub.concurrencylab.ui.device.DeviceViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EcoHubAppRoot()
        }
    }
}

@Composable
private fun EcoHubAppRoot() {
    val vm: DeviceViewModel = hiltViewModel()
    val state by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(vm.effects) {
        vm.effects.collect { effect ->
            when (effect) {
                is DeviceUiEffect.ShowSnackbar ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    DeviceScreen(
        state = state,
        onIntent = vm::onIntent,
        snackbarHostState = snackbarHostState
    )
}
