package com.yourapp.focusflow.feature_achievement.domain.usecase

import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Checks if the user has reached any new milestones after completing a task.
 * Returns the ID of the unlocked achievement, or null if none.
 */
class TrackProgress @Inject constructor(
    private val taskRepository: TaskRepository,
    private val unlockAchievement: UnlockAchievement
) {
    suspend operator fun invoke(): String? {
        val completedCount = taskRepository.getCompletedTasksCount()
        
        val achievementToUnlock = when (completedCount) {
            1 -> "first_task"
            5 -> "five_tasks"
            10 -> "ten_tasks"
            else -> null
        }

        return if (achievementToUnlock != null) {
            unlockAchievement(achievementToUnlock)
            achievementToUnlock
        } else {
            null
        }
    }
}
