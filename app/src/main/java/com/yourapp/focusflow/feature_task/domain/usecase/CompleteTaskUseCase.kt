package com.yourapp.focusflow.feature_task.domain.usecase

import com.yourapp.focusflow.feature_affirmation.domain.usecase.GenerateAffirmation
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val generateAffirmation: GenerateAffirmation
) {
    suspend operator fun invoke(taskId: String): String? {
        val task = repository.getTaskById(taskId)
        return if (task != null) {
            // Mark as completed. 
            // Depending on preference, we could also delete it: repository.deleteTask(task)
            repository.insertTask(task.copy(isCompleted = true))
            
            // Return the dopamine-hit affirmation
            generateAffirmation()
        } else {
            null
        }
    }
}
