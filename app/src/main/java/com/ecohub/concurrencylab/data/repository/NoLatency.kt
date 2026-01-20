package com.ecohub.concurrencylab.data.repository

object NoLatency : LatencyProvider {
    override suspend fun delay() = Unit
}
