package com.ecohub.concurrencylab.data.repository

import com.ecohub.concurrencylab.data.error.ConflictException
import org.junit.Assert.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeDeviceRepositoryTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    @Test
    fun `setTemperature updates state when version matches`() = runTest(dispatcher) {
        val repo = FakeDeviceRepository(technicianIntervalMillis = Long.MAX_VALUE, scope = scope)

        repo.setTemperature(newTemp = 22.5, expectedVersion = 0L)

        val state = repo.deviceState.value
        assertEquals(22.5, state.temperature, 0.0)
        assertEquals(1L, state.version)
        repo.close()
    }

    @Test
    fun `setTemperature throws conflict when version mismatches`() = runTest(dispatcher) {
        val repo = FakeDeviceRepository(technicianIntervalMillis = Long.MAX_VALUE, scope = scope)

        try {
            repo.setTemperature(newTemp = 21.0, expectedVersion = 5L)
            fail("Expected ConflictException")
        } catch (_: ConflictException) {
        }

        val state = repo.deviceState.value
        assertEquals(20.0, state.temperature, 0.0)
        assertEquals(0L, state.version)
        repo.close()
    }

    @Test
    fun `technician job updates temperature and increments version`() = runTest(dispatcher) {
        val repo = FakeDeviceRepository(technicianIntervalMillis = 15_000L, scope = scope)

        advanceTimeBy(15_000L)
        runCurrent()

        val state = repo.deviceState.value
        assertEquals(20.5, state.temperature, 0.0)
        assertEquals(1L, state.version)
        repo.close()
    }

    @Test
    fun `user update conflicts when technician advanced version`() = runTest(dispatcher) {
        val repo = FakeDeviceRepository(technicianIntervalMillis = 15_000L, scope = scope)

        val expectedVersion = repo.deviceState.value.version
        advanceTimeBy(15_000L)
        runCurrent()

        val conflict = try {
            repo.setTemperature(newTemp = 22.0, expectedVersion = expectedVersion)
            null
        } catch (e: ConflictException) {
            e
        }

        assertNotNull(conflict)
        val latest = requireNotNull(conflict).latest
        assertEquals(20.5, latest.temperature, 0.0)
        assertEquals(1L, latest.version)
        repo.close()
    }
}
