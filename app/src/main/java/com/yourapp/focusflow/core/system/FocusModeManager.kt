package com.yourapp.focusflow.core.system

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.yourapp.focusflow.worker.FocusWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The brain of your focus sessions.
 * Coordinates between the UI, the AppBlocker (Accessibility Service), 
 * and the WorkManager (Background processing).
 */
@Singleton
class FocusModeManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationHelper: NotificationHelper,
) {
    private val workManager = WorkManager.getInstance(context)
    
    private val _isFocusActive = MutableStateFlow(false)
    val isFocusActive: StateFlow<Boolean> = _isFocusActive.asStateFlow()

    /**
     * Starts a focus session.
     * @param durationMins How long the session should last.
     * @param blockedApps List of package names to block (e.g., "com.instagram.android").
     */
    fun startFocusSession(durationMins: Int, blockedApps: Set<String>) {
        if (_isFocusActive.value) return

        // 1. Enable App Blocking via the Accessibility Service
        AppBlocker.updateBlockedApps(blockedApps)
        AppBlocker.isBlockingActive = true

        // 2. Schedule the FocusWorker to track time in the background
        val focusRequest = OneTimeWorkRequestBuilder<FocusWorker>()
            .setInputData(workDataOf("DURATION_MINS" to durationMins))
            .build()

        workManager.enqueueUniqueWork(
            FOCUS_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            focusRequest
        )

        // 3. Update state and notify user
        _isFocusActive.value = true
        notificationHelper.showReminder(
            "Focus Mode Active", 
            "Keep it up! Blocking distractions for $durationMins mins."
        )
    }

    /**
     * Stops the current focus session and cleans up.
     */
    fun stopFocusSession() {
        if (!_isFocusActive.value) return

        // 1. Disable App Blocking
        AppBlocker.isBlockingActive = false
        
        // 2. Cancel the background worker
        workManager.cancelUniqueWork(FOCUS_WORK_NAME)

        // 3. Update state and notify user
        _isFocusActive.value = false
        notificationHelper.showReminder("Focus Session Ended", "You did it! Take a break.")
    }

    companion object {
        private const val FOCUS_WORK_NAME = "focus_session_worker"
    }
}
