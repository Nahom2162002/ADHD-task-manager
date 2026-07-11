package com.yourapp.focusflow.feature_achievement.data.repository

import com.yourapp.focusflow.feature_achievement.data.local.AchievementDao
import com.yourapp.focusflow.feature_achievement.data.local.AchievementEntity
import com.yourapp.focusflow.feature_achievement.domain.model.Achievement
import com.yourapp.focusflow.feature_achievement.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao
) : AchievementRepository {

    override fun getAchievements(): Flow<List<Achievement>> {
        return achievementDao.getAllAchievements().map { entities ->
            entities.map { it.toAchievement() }
        }
    }

    override suspend fun unlockAchievement(id: String) {
        achievementDao.unlockAchievement(id, System.currentTimeMillis())
    }

    private fun AchievementEntity.toAchievement(): Achievement {
        return Achievement(
            id = id,
            title = title,
            description = description,
            iconResId = android.R.drawable.star_on, // Placeholder icon
            isUnlocked = isUnlocked,
            unlockedAt = unlockedAt
        )
    }
}
