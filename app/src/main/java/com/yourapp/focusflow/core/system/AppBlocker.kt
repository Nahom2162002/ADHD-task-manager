package com.yourapp.focusflow.core.system

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppBlocker @Inject constructor() {
    fun isBlockingEnabled(): Boolean = false
}
