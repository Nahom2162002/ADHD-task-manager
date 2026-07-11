package com.yourapp.focusflow.feature_task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourapp.focusflow.feature_achievement.data.local.AchievementDao
import com.yourapp.focusflow.feature_achievement.data.local.AchievementEntity

@Database(
    entities = [TaskEntity::class, AchievementEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class FocusFlowDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun achievementDao(): AchievementDao
}
