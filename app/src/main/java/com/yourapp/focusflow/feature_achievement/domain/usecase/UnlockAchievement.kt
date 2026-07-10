package com.yourapp.focusflow.feature_achievement.domain.usecase

import com.yourapp.focusflow.feature_achievement.domain.repository.AchievementRepository
import javax.inject.Inject

class UnlockAchievement @Inject constructor(
    private val repository: AchievementRepository
) {
    suspend operator fun invoke(achievementId: String) {
        repository.unlockAchievement(achievementId)
    }
}
