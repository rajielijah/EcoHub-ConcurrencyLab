package com.ecohub.concurrencylab.ui.preferences

class FakeUiPreferences : UiPreferences {

    private var collaborativeMode = false

    override fun isCollaborativeModeEnabled(): Boolean {
        return collaborativeMode
    }

    override fun setCollaborativeModeEnabled(enabled: Boolean) {
        collaborativeMode = enabled
    }
}
