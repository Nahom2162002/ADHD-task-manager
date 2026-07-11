package com.yourapp.focusflow.core.system

import javax.inject.Inject
import javax.inject.Singleton
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class AppBlocker : AccessibilityService() {
    companion object {
        var isBlockingActive = false
        var blockedPackages = mutableSetOf<String>()

        fun updateBlockedApps(packages: Set<String>) {
            blockedPackages.clear()
            blockedPackages.addAll(packages)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!isBlockingActive || event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        if (blockedPackages.contains(packageName)) {
            performGlobalAction(GLOBAL_ACTION_HOME)
            Toast.makeText(this, "FocusFlow: Stay focused! \uD83D\uDE80", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInterrupt() {}

    override fun onServiceConnected() {
        super.onServiceConnected()

        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.DEFAULT
            notificationTimeout = 100
        }
        this.serviceInfo = info
    }
}
