package com.yourapp.focusflow.feature_break.domain.usecase

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.yourapp.focusflow.core.system.FocusModeManager
import com.yourapp.focusflow.worker.BreakWorker
import javax.inject.Inject

/**
 * Executes the transition from focus to break.
 * For ADHD users, this automation is key to preventing "hyperfocus burnout."
 */
class TriggerBreak @Inject constructor(
    private val focusModeManager: FocusModeManager,
    private val workManager: WorkManager,
    private val calculateBreakTiming: CalculateBreakTiming
) {
    operator fun invoke(sessionDurationMins: Int) {
        // 1. End the active focus session (lifts app blocks)
        focusModeManager.stopFocusSession()

        // 2. Calculate how long the break should be
        val breakMins = calculateBreakTiming(sessionDurationMins)

        // 3. Schedule the BreakWorker
        val breakRequest = OneTimeWorkRequestBuilder<BreakWorker>()
            .setInputData(workDataOf("BREAK_DURATION_MINS" to breakMins))
            .build()

        workManager.enqueueUniqueWork(
            "break_session",
            ExistingWorkPolicy.REPLACE,
            breakRequest
        )
    }
}
