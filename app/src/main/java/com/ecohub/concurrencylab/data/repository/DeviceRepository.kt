package com.ecohub.concurrencylab.data.repository

import com.ecohub.concurrencylab.data.model.DeviceState
import kotlinx.coroutines.flow.StateFlow

interface DeviceRepository {
    val deviceState: StateFlow<DeviceState>

    val minTemperature: Double
    val maxTemperature: Double

    suspend fun setTemperature(newTemp: Double, expectedVersion: Long)

    suspend fun forceUpdate(newTemp: Double)
}

