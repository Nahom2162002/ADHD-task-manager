package com.yourapp.focusflow.feature_achievement.domain.repository

import com.yourapp.focusflow.feature_achievement.domain.model.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    fun getAchievements(): Flow<List<Achievement>>
    suspend fun unlockAchievement(id: String)
}
