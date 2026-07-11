package com.yourapp.focusflow.feature_task.domain.usecase

import com.yourapp.focusflow.feature_achievement.domain.usecase.TrackProgress
import com.yourapp.focusflow.feature_affirmation.domain.usecase.GenerateAffirmation
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

data class TaskCompletionResult(
data class CompletionResult(
    val affirmation: String,
    val unlockedAchievementId: String? = null
)

class CompleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val generateAffirmation: GenerateAffirmation,
    private val trackProgress: TrackProgress
) {
    suspend operator fun invoke(taskId: String): TaskCompletionResult? {
    suspend operator fun invoke(taskId: String): CompletionResult? {
        val task = repository.getTaskById(taskId)
        return if (task != null) {
            // 1. Mark as completed in DB
            repository.insertTask(task.copy(isCompleted = true))
            
            // 2. Check for achievement milestones
            val achievementId = trackProgress()
            
            // 3. Return the dopamine-hit affirmation and any new achievement
            TaskCompletionResult(
            // 3. Return both the dopamine-hit affirmation and any new achievement
            CompletionResult(
                affirmation = generateAffirmation(),
                unlockedAchievementId = achievementId
            )
        } else {
            null
        }
    }
}
