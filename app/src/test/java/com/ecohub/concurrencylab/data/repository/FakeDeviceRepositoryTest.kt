package com.ecohub.concurrencylab.data.repository

import com.ecohub.concurrencylab.data.error.ConflictException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeDeviceRepositoryTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private fun createRepo(
        technicianIntervalMillis: Long = Long.MAX_VALUE
    ): FakeDeviceRepository =
        FakeDeviceRepository(
            technicianIntervalMillis = technicianIntervalMillis,
            scope = scope
        )


    @Test
    fun `setTemperature updates state when version matches`() = runTest(dispatcher) {
        val repo = createRepo()

        try {
            repo.setTemperature(
                newTemp = 22.5,
                expectedVersion = 0L
            )

            val state = repo.deviceState.value
            assertEquals(22.5, state.temperature, 0.0)
            assertEquals(1L, state.version)
        } finally {
            repo.close()
        }
    }

    @Test
    fun `setTemperature clamps value using repository contract`() = runTest(dispatcher) {
        val repo = createRepo()

        try {
            val max = repo.maxTemperature
            val expectedVersion = repo.deviceState.value.version

            repo.setTemperature(
                newTemp = max + 100,
                expectedVersion = expectedVersion
            )

            val state = repo.deviceState.value
            assertEquals(max, state.temperature, 0.0)
            assertEquals(expectedVersion + 1, state.version)
        } finally {
            repo.close()
        }
    }


    @Test
    fun `setTemperature throws conflict and does not mutate state when version mismatches`() =
        runTest(dispatcher) {

            val repo = createRepo()

            try {
                val conflict = try {
                    repo.setTemperature(
                        newTemp = 21.0,
                        expectedVersion = 5L
                    )
                    null
                } catch (e: ConflictException) {
                    e
                }

                assertNotNull(conflict)

                val latest = requireNotNull(conflict).latest
                assertEquals(20.0, latest.temperature, 0.0)
                assertEquals(0L, latest.version)

                val state = repo.deviceState.value
                assertEquals(20.0, state.temperature, 0.0)
                assertEquals(0L, state.version)
            } finally {
                repo.close()
            }
        }

    @Test
    fun `technician job advances temperature and version deterministically`() =
        runTest(dispatcher) {

            val repo = createRepo(
                technicianIntervalMillis = 15_000L
            )

            try {
                advanceTimeBy(15_000L)
                runCurrent()

                val first = repo.deviceState.value
                assertEquals(20.5, first.temperature, 0.0)
                assertEquals(1L, first.version)

                advanceTimeBy(15_000L)
                runCurrent()

                val second = repo.deviceState.value
                assertEquals(21.0, second.temperature, 0.0)
                assertEquals(2L, second.version)
            } finally {
                repo.close()
            }
        }

    @Test
    fun `user update conflicts when technician advances version`() =
        runTest(dispatcher) {

            val repo = createRepo(
                technicianIntervalMillis = 15_000L
            )

            try {
                val expectedVersion = repo.deviceState.value.version

                advanceTimeBy(15_000L)
                runCurrent()

                val conflict = try {
                    repo.setTemperature(
                        newTemp = 22.0,
                        expectedVersion = expectedVersion
                    )
                    null
                } catch (e: ConflictException) {
                    e
                }
                assertNotNull(conflict)

                val latest = requireNotNull(conflict).latest
                assertEquals(20.5, latest.temperature, 0.0)
                assertEquals(1L, latest.version)

                val state = repo.deviceState.value
                assertEquals(20.5, state.temperature, 0.0)
                assertEquals(1L, state.version)
            } finally {
                repo.close()
            }
        }

}
