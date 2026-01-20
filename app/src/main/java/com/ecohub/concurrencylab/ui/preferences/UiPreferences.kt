package com.ecohub.concurrencylab.ui.preferences

interface UiPreferences {
    fun isCollaborativeModeEnabled(): Boolean
    fun setCollaborativeModeEnabled(enabled: Boolean)
}
