package com.ecohub.concurrencylab.data.repository

import com.ecohub.concurrencylab.data.error.ConflictException
import com.ecohub.concurrencylab.data.model.DeviceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class FakeDeviceRepository(
    private val technicianIntervalMillis: Long = TECHNICIAN_INTERVAL_MILLIS,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : DeviceRepository {

    private val mutex = Mutex()
    private val _deviceState = MutableStateFlow(DeviceState(temperature = 20.0, version = 0L))
    override val deviceState: StateFlow<DeviceState> = _deviceState.asStateFlow()

    private val technicianJob: Job = scope.launch {
        while (isActive) {
            delay(technicianIntervalMillis)
            advanceTechnicianReading()
        }
    }

    override suspend fun setTemperature(newTemp: Double, expectedVersion: Long) {
        mutex.withLock {
            val current = _deviceState.value
            if (current.version != expectedVersion) {
                throw ConflictException(current)
            }

            val next = current.copy(
                temperature = newTemp,
                version = current.version + 1
            )
            _deviceState.value = next
        }
    }

    override suspend fun forceUpdate(newTemp: Double) {
        mutex.withLock {
            val current = _deviceState.value
            val next = current.copy(
                temperature = newTemp,
                version = current.version + 1
            )
            _deviceState.value = next
        }
    }

    fun close() {
        technicianJob.cancel()
        scope.cancel()
    }

    private suspend fun advanceTechnicianReading() {
        mutex.withLock {
            val current = _deviceState.value
            val nextTemperature = computeNextTechnicianTemperature(current.temperature)
            val next = current.copy(
                temperature = nextTemperature,
                version = current.version + 1
            )
            _deviceState.value = next
        }
    }

    private fun computeNextTechnicianTemperature(current: Double): Double {
        val step = 0.5
        val upperBound = 30.0
        val lowerBound = 18.0

        return when {
            current >= upperBound -> lowerBound
            else -> current + step
        }
    }

    companion object {
        private const val TECHNICIAN_INTERVAL_MILLIS = 15_000L
    }
}

