package com.yourapp.focusflow.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.yourapp.focusflow.feature_achievement.data.local.AchievementDao
import com.yourapp.focusflow.feature_achievement.data.local.AchievementEntity
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FocusFlowApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var achievementDao: AchievementDao

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        seedAchievements()
    }

    private fun seedAchievements() {
        CoroutineScope(Dispatchers.IO).launch {
            // Seeding initial achievements if they don't exist
            val achievements = listOf(
                AchievementEntity(
                    id = "first_task",
                    title = "First Step",
                    description = "Complete your first task."
                ),
                AchievementEntity(
                    id = "five_tasks",
                    title = "High Five",
                    description = "Complete 5 tasks."
                ),
                AchievementEntity(
                    id = "ten_tasks",
                    title = "Flow Master",
                    description = "Complete 10 tasks."
                )
            )
            achievements.forEach { 
                if (achievementDao.getAchievementById(it.id) == null) {
                    achievementDao.insertAchievement(it)
                }
            }
        }
    }
}
