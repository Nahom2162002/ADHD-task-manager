package com.yourapp.focusflow.feature_break.domain.usecase

import com.yourapp.focusflow.core.util.Constants
import javax.inject.Inject

/**
 * Determines the appropriate break length.
 * For ADHD users, frequent intervals (e.g., 25/5 or 50/10) are 
 * often more sustainable than long marathons.
 */
class CalculateBreakTiming @Inject constructor() {
    
    operator fun invoke(sessionDurationMins: Int): Int {
        return if (sessionDurationMins >= 50) {
            Constants.LONG_BREAK_DURATION_MINS
        } else {
            Constants.SHORT_BREAK_DURATION_MINS
        }
    }
}
