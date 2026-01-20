package com.ecohub.concurrencylab.data.repository

import com.ecohub.concurrencylab.data.error.ConflictException
import com.ecohub.concurrencylab.data.model.DeviceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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

    private val minTemp: Double = MIN_TEMP,
    private val maxTemp: Double = MAX_TEMP,

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),

) : DeviceRepository {

    private val mutex = Mutex()
    private val _deviceState = MutableStateFlow(DeviceState(temperature = DEFAULT_TEMP, version = 0L))
    override val deviceState: StateFlow<DeviceState> = _deviceState.asStateFlow()

    override val minTemperature: Double = minTemp
    override val maxTemperature: Double = maxTemp


    private val technicianJob: Job = scope.launch {
        while (isActive) {
            delay(technicianIntervalMillis)
            advanceTechnicianReading()
        }
    }

    private fun clampTemperature(value: Double): Double =
        value.coerceIn(minTemp, maxTemp)

    override suspend fun setTemperature(newTemp: Double, expectedVersion: Long) {
        mutex.withLock {
            val current = _deviceState.value

            if (current.version != expectedVersion) {
                throw ConflictException(current)
            }

            val next = current.copy(
                temperature = clampTemperature(newTemp),
                version = current.version + 1
            )
            _deviceState.value = next

        }
    }

    override suspend fun forceUpdate(newTemp: Double) {
        mutex.withLock {
            val current = _deviceState.value
            val next = current.copy(
                temperature = clampTemperature(newTemp),
                version = current.version + 1
            )
            _deviceState.value = next

        }
    }

    private suspend fun advanceTechnicianReading() {
        mutex.withLock {
            val current = _deviceState.value
            val nextTemperature =
                computeNextTechnicianTemperature(current.temperature)

            _deviceState.value = current.copy(
                temperature = clampTemperature(nextTemperature),
                version = current.version + 1
            )
        }
    }

    private fun computeNextTechnicianTemperature(current: Double): Double {
        return current + TECHNICIAN_STEP
    }


    fun close() {
        technicianJob.cancel()
        scope.cancel()
    }

    companion object {
        private const val TECHNICIAN_INTERVAL_MILLIS = 15_000L
        private const val TECHNICIAN_STEP = 0.5
        private const val MIN_TEMP = 5.0
        private const val MAX_TEMP = 30.0
        private const val DEFAULT_TEMP = 20.0

    }
}

