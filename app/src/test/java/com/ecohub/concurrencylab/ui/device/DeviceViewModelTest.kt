package com.ecohub.concurrencylab.ui.device

import com.ecohub.concurrencylab.data.repository.DeviceRepository
import com.ecohub.concurrencylab.data.repository.FakeDeviceRepository
import com.ecohub.concurrencylab.testutil.MainDispatcherRule
import com.ecohub.concurrencylab.ui.preferences.FakeUiPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeviceViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: DeviceRepository
    private lateinit var uiPreferences: FakeUiPreferences
    private lateinit var viewModel: DeviceViewModel

    private fun createViewModel() {
        repository = FakeDeviceRepository(
            technicianIntervalMillis = Long.MAX_VALUE
        )
        uiPreferences = FakeUiPreferences()
        viewModel = DeviceViewModel(repository, uiPreferences)
    }

    @Test
    fun `initial state reflects repository and preferences`() = runTest {
        createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(20.0, state.temperature)
        assertEquals("v0", state.versionLabel)
        assertEquals(false, state.collaborativeMode)
    }

    @Test
    fun `valid input updates temperature`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("22.0"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(22.0, state.temperature)
        assertEquals("v1", state.versionLabel)
    }

    @Test
    fun `invalid input emits snackbar`() = runTest {
        createViewModel()

        val effects = mutableListOf<DeviceUiEffect>()
        val job = launch {
            viewModel.effects.collect { effects += it }
        }

        yield()

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("abc"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        assertEquals(1, effects.size)
        assertEquals(
            "Enter a valid temperature",
            (effects.first() as DeviceUiEffect.ShowSnackbar).message
        )

        job.cancel()
    }


    @Test
    fun `collaborative mode conflict emits snackbar and no dialog`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.CollaborativeModeToggled(true))
        repository.forceUpdate(21.0)

        val effects = mutableListOf<DeviceUiEffect>()
        val job = launch {
            viewModel.effects.collect { effects += it }
        }

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("22.0"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(null, state.conflictDialog)
        assertEquals(1, effects.size)

        job.cancel()
    }


    @Test
    fun `option B feedback emits adjusted message when clamped`() = runTest {
        createViewModel()

        val effects = mutableListOf<DeviceUiEffect>()
        val job = launch {
            viewModel.effects.collect { effects += it }
        }

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("100"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        assertEquals(30.0, viewModel.uiState.value.temperature)
        assertEquals(
            "Temperature adjusted to 30.0°C",
            (effects.last() as DeviceUiEffect.ShowSnackbar).message
        )

        job.cancel()
    }
}
