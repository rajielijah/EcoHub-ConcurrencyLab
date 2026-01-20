package com.ecohub.concurrencylab

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecohub.concurrencylab.data.repository.FakeDeviceRepository
import com.ecohub.concurrencylab.data.repository.RandomLatency
import com.ecohub.concurrencylab.ui.device.DeviceScreen
import com.ecohub.concurrencylab.ui.device.DeviceUiEffect
import com.ecohub.concurrencylab.ui.device.DeviceViewModel
import com.ecohub.concurrencylab.ui.preferences.AndroidUiPreferences
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    private val repository by lazy { FakeDeviceRepository(
//        latencyProvider = RandomLatency(maxMillis = 2_000),
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoHubApp(
                repository = repository,
                uiPreferences = AndroidUiPreferences(application)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
private fun EcoHubApp(repository: FakeDeviceRepository, uiPreferences: AndroidUiPreferences,) {
    val vm: DeviceViewModel = viewModel(factory = DeviceViewModelFactory(repository, uiPreferences))
    val state by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(vm.effects) {
        vm.effects.collect { effect ->
            when (effect) {
                is DeviceUiEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    DeviceScreen(
        state = state,
        onIntent = vm::onIntent,
        snackbarHostState = snackbarHostState
    )
}

private class DeviceViewModelFactory(
    private val repository: FakeDeviceRepository,
    private val uiPreferences: AndroidUiPreferences,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeviceViewModel(repository, uiPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

