package com.ecohub.concurrencylab.data.error

import com.ecohub.concurrencylab.data.model.DeviceState

class ConflictException(val latest: DeviceState) : Exception(
    "Version conflict: expected version does not match current version ${latest.version}"
)
