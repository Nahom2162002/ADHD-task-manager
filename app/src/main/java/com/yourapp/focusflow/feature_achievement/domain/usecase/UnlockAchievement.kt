package com.yourapp.focusflow.feature_achievement.domain.usecase

import javax.inject.Inject

class UnlockAchievement @Inject constructor() {
    operator fun invoke(achievementId: String): Boolean = achievementId.isNotBlank()
}
