package com.yourapp.focusflow.feature_break.domain.usecase

import com.yourapp.focusflow.core.system.FocusModeManager
import com.yourapp.focusflow.core.system.NotificationHelper
import javax.inject.Inject

/**
 * Executes the transition from focus to break.
 * It alerts the user and temporarily lifts the app restrictions.
 */
class TriggerBreak @Inject constructor(
    private val focusModeManager: FocusModeManager,
    private val notificationHelper: NotificationHelper
) {
    operator fun invoke() {
        // 1. Lift app blocking so the user can use their phone during the break
        focusModeManager.stopFocusSession()
        
        // 2. Notify the user that it's time to step away
        notificationHelper.showReminder(
            title = "Time for a break! ☕",
            message = "You've worked hard. Step away for a few minutes to recharge."
        )
    }
}
