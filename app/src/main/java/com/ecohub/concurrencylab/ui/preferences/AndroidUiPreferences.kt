package com.ecohub.concurrencylab.ui.preferences

import android.app.Application
import android.content.Context
import androidx.core.content.edit

class AndroidUiPreferences(
    application: Application
) : UiPreferences {

    private val prefs = application.getSharedPreferences(
        "ui_prefs",
        Context.MODE_PRIVATE
    )

    override fun isCollaborativeModeEnabled(): Boolean {
        return prefs.getBoolean("collaborative_mode", false)
    }

    override fun setCollaborativeModeEnabled(enabled: Boolean) {
        prefs.edit {
            putBoolean("collaborative_mode", enabled)
        }
    }
}
