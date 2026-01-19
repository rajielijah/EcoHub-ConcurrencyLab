package com.ecohub.concurrencylab.ui.device

import app.cash.turbine.test
import com.ecohub.concurrencylab.data.error.ConflictException
import com.ecohub.concurrencylab.data.model.DeviceState
import com.ecohub.concurrencylab.data.repository.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeviceViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `successful user update emits success snackbar and updates ui`() = runTest(dispatcher) {
        val repo = TestDeviceRepository()
        val vm = DeviceViewModel(repo)
        runCurrent()

        vm.effects.test {
            vm.onIntent(DeviceIntent.TemperatureInputChanged("22.5"))
            vm.onIntent(DeviceIntent.SetTemperatureClicked)
            runCurrent()

            val effect = awaitItem()
            assertEquals("Temperature updated to 22.5°C", (effect as DeviceUiEffect.ShowSnackbar).message)
            expectNoEvents()
        }

        val state = vm.uiState.value
        assertEquals("22.5°C", state.temperatureText)
        assertEquals("v1", state.versionLabel)
        assertNull(state.conflictDialog)
    }

    @Test
    fun `collaborative mode conflict auto resolves and shows technician snackbar`() = runTest(dispatcher) {
        val repo = TestDeviceRepository()
        val vm = DeviceViewModel(repo)
        runCurrent()

        vm.onIntent(DeviceIntent.CollaborativeModeToggled(true))
        vm.onIntent(DeviceIntent.TemperatureInputChanged("21.0"))
        repo.enqueueConflict(DeviceState(temperature = 25.0, version = 1L))

        vm.effects.test {
            vm.onIntent(DeviceIntent.SetTemperatureClicked)
            runCurrent()

            val effect = awaitItem()
            assertEquals("Updated by technician to 25.0°C", (effect as DeviceUiEffect.ShowSnackbar).message)
            expectNoEvents()
        }

        val state = vm.uiState.value
        assertEquals("25.0°C", state.temperatureText)
        assertEquals("v1", state.versionLabel)
        assertNull(state.conflictDialog)
    }

    @Test
    fun `manual conflict shows dialog without snackbar`() = runTest(dispatcher) {
        val repo = TestDeviceRepository()
        val vm = DeviceViewModel(repo)
        runCurrent()

        vm.onIntent(DeviceIntent.TemperatureInputChanged("21.0"))
        repo.enqueueConflict(DeviceState(temperature = 24.0, version = 1L))

        vm.effects.test {
            vm.onIntent(DeviceIntent.SetTemperatureClicked)
            runCurrent()
            expectNoEvents()
        }

        val state = vm.uiState.value
        assertEquals("24.0°C", state.temperatureText)
        assertEquals("v1", state.versionLabel)
        val conflict = state.conflictDialog
        requireNotNull(conflict)
        assertEquals(21.0, conflict.userAttemptedTemp, 0.0)
        assertEquals(0L, conflict.expectedVersion)
        assertEquals(24.0, conflict.technicianTemp, 0.0)
        assertEquals(1L, conflict.technicianVersion)
    }

    @Test
    fun `overwrite after manual conflict forces user value and clears dialog`() = runTest(dispatcher) {
        val repo = TestDeviceRepository()
        val vm = DeviceViewModel(repo)
        runCurrent()

        vm.onIntent(DeviceIntent.TemperatureInputChanged("23.0"))
        repo.enqueueConflict(DeviceState(temperature = 24.0, version = 1L))
        vm.onIntent(DeviceIntent.SetTemperatureClicked)
        runCurrent()

        vm.effects.test {
            vm.onIntent(DeviceIntent.ConflictForceOverwriteChosen)
            runCurrent()
            expectNoEvents()
        }

        val state = vm.uiState.value
        assertEquals("23.0°C", state.temperatureText)
        assertEquals("v2", state.versionLabel)
        assertNull(state.conflictDialog)
    }

    @Test
    fun `keep technician after manual conflict keeps technician value and clears dialog`() = runTest(dispatcher) {
        val repo = TestDeviceRepository()
        val vm = DeviceViewModel(repo)
        runCurrent()

        vm.onIntent(DeviceIntent.TemperatureInputChanged("23.0"))
        repo.enqueueConflict(DeviceState(temperature = 24.0, version = 1L))
        vm.onIntent(DeviceIntent.SetTemperatureClicked)
        runCurrent()

        vm.effects.test {
            vm.onIntent(DeviceIntent.ConflictKeepTechnicianChosen)
            runCurrent()
            expectNoEvents()
        }

        val state = vm.uiState.value
        assertEquals("24.0°C", state.temperatureText)
        assertEquals("v1", state.versionLabel)
        assertNull(state.conflictDialog)
    }
}

private class TestDeviceRepository(
    initialTemperature: Double = 20.0,
    initialVersion: Long = 0L
) : DeviceRepository {

    private val _deviceState = MutableStateFlow(DeviceState(initialTemperature, initialVersion))
    override val deviceState: StateFlow<DeviceState> = _deviceState.asStateFlow()

    private var nextConflictLatest: DeviceState? = null

    override suspend fun setTemperature(newTemp: Double, expectedVersion: Long) {
        nextConflictLatest?.let { latest ->
            nextConflictLatest = null
            _deviceState.value = latest
            throw ConflictException(latest)
        }

        val current = _deviceState.value
        if (current.version != expectedVersion) {
            throw ConflictException(current)
        }

        _deviceState.value = current.copy(
            temperature = newTemp,
            version = current.version + 1
        )
    }

    override suspend fun forceUpdate(newTemp: Double) {
        val current = _deviceState.value
        _deviceState.value = current.copy(
            temperature = newTemp,
            version = current.version + 1
        )
    }

    fun enqueueConflict(latest: DeviceState) {
        nextConflictLatest = latest
    }
}
