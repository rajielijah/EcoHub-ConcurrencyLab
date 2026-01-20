package com.ecohub.concurrencylab.data.repository

import android.content.Context
import android.content.SharedPreferences
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
import kotlin.math.min
import androidx.core.content.edit

class FakeDeviceRepository(

    private val technicianIntervalMillis: Long = TECHNICIAN_INTERVAL_MILLIS,

    private val minTemp: Double = MIN_TEMP,

    private val maxTemp: Double = MAX_TEMP,

//    private val latencyProvider: LatencyProvider = NoLatency,

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
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

    private fun clampTemperature(value: Double): Double {
        return value.coerceIn(minTemp, maxTemp)
    }


    override suspend fun setTemperature(newTemp: Double, expectedVersion: Long) {

//        latencyProvider.delay()
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

        return if (current < upperBound) {
            current + step
        } else {
            current
        }
    }


    companion object {
        private const val TECHNICIAN_INTERVAL_MILLIS = 15_000L
        private const val MIN_TEMP = 5.0
        private const val MAX_TEMP = 30.0

    }
}

