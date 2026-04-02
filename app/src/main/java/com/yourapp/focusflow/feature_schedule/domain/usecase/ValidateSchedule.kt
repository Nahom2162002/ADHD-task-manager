package com.yourapp.focusflow.feature_schedule.domain.usecase

import javax.inject.Inject

class ValidateSchedule @Inject constructor() {
    operator fun invoke(schedule: List<String>): Boolean = schedule.isNotEmpty()
}
