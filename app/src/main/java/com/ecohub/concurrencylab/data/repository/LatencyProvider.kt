package com.ecohub.concurrencylab.data.repository

interface LatencyProvider {
    suspend fun delay()
}
