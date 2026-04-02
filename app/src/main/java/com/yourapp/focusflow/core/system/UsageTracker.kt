package com.yourapp.focusflow.core.system

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageTracker @Inject constructor() {
    fun recordUsageEvent(packageName: String) {}
}
