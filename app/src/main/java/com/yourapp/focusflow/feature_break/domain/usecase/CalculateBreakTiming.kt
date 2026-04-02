package com.yourapp.focusflow.feature_break.domain.usecase

import javax.inject.Inject

class CalculateBreakTiming @Inject constructor() {
    fun nextBreakAfterWorkMillis(workDurationMillis: Long): Long = workDurationMillis
}
