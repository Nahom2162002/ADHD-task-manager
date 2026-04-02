package com.yourapp.focusflow.core.system

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusModeManager @Inject constructor() {
    fun isFocusModeActive(): Boolean = false
}
