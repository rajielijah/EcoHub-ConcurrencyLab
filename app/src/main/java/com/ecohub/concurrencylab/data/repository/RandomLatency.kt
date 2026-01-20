package com.ecohub.concurrencylab.data.repository

import kotlinx.coroutines.delay
import kotlin.random.Random

class RandomLatency(
    private val maxMillis: Long = 2_000L
) : LatencyProvider {

    override suspend fun delay() {
        val duration = Random.nextLong(0, maxMillis + 1)
        delay(duration)
    }
}
