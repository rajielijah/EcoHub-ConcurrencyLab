package com.ecohub.concurrencylab.ui.device

import com.ecohub.concurrencylab.data.repository.DeviceRepository
import com.ecohub.concurrencylab.data.repository.FakeDeviceRepository
import com.ecohub.concurrencylab.testutil.MainDispatcherRule
import com.ecohub.concurrencylab.ui.preferences.FakeUiPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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

        viewModel = DeviceViewModel(
            repository = repository,
            uiPreferences = uiPreferences
        )
    }

    @Test
    fun `initial state reflects repository temperature and preferences`() = runTest {
        createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("20.0°C", state.temperatureText)
        assertEquals("v0", state.versionLabel)
        assertEquals(false, state.collaborativeMode)
    }

    @Test
    fun `temperature input updates ui state`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("23.5"))

        val state = viewModel.uiState.value
        assertEquals("23.5", state.temperatureInput)
    }

    @Test
    fun `set temperature updates repository and ui state`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("22.0"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("22.0°C", state.temperatureText)
        assertEquals("v1", state.versionLabel)
    }

    @Test
    fun `invalid temperature input emits snackbar effect`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("abc"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        val effect = viewModel.effects.replayCache.firstOrNull()
        assertNotNull(effect)
    }

    @Test
    fun `collaborative mode toggle updates state and preferences`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.CollaborativeModeToggled(true))

        val state = viewModel.uiState.value
        assertEquals(true, state.collaborativeMode)
        assertEquals(true, uiPreferences.isCollaborativeModeEnabled())
    }

//    @Test
//    fun `conflict shows dialog when collaborative mode is off`() = runTest {
//        createViewModel()
//
//        repository.forceUpdate(21.0)
//
//        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("22.0"))
//        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)
//
//        advanceUntilIdle()
//
//        val state = viewModel.uiState.value
//        assertNotNull(state.conflictDialog)
//        assertEquals(21.0, state.conflictDialog!!.technicianTemp, 0.0)
//    }

    @Test
    fun `conflict in collaborative mode emits snackbar instead of dialog`() = runTest {
        createViewModel()

        viewModel.onIntent(DeviceIntent.CollaborativeModeToggled(true))

        repository.forceUpdate(21.0)

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("22.0"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(null, state.conflictDialog)

        val effect = viewModel.effects.replayCache.firstOrNull()
        assertNotNull(effect)
    }

    @Test
    fun `force overwrite resolves conflict and clears dialog`() = runTest {
        createViewModel()

        repository.forceUpdate(21.0)

        viewModel.onIntent(DeviceIntent.TemperatureInputChanged("23.0"))
        viewModel.onIntent(DeviceIntent.SetTemperatureClicked)

        advanceUntilIdle()

        viewModel.onIntent(DeviceIntent.ConflictForceOverwriteChosen)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(null, state.conflictDialog)
        assertEquals("23.0°C", state.temperatureText)
    }
}
