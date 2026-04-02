package com.yourapp.focusflow.feature_achievement.domain.usecase

import javax.inject.Inject

class TrackProgress @Inject constructor() {
    operator fun invoke(completedTasks: Int): Int = completedTasks
}
