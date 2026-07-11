package com.yourapp.focusflow.feature_task.domain.usecase

import com.yourapp.focusflow.core.util.Constants
import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Handles adding a new task.
 * Includes ADHD-specific logic to prevent "Task Hoarding" by enforcing 
 * the MAX_ACTIVE_TASKS limit.
 */
class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    sealed class Result {
        object Success : Result()
        data class Error(val message: String) : Result()
    }

    suspend operator fun invoke(task: Task): Result {
        val activeTasksCount = repository.getTasks().first().count { !it.isCompleted }
        
        return if (activeTasksCount >= Constants.MAX_ACTIVE_TASKS) {
            Result.Error("Let's focus on your current ${Constants.MAX_ACTIVE_TASKS} tasks first to avoid overwhelm! 🧠")
        } else {
            repository.insertTask(task)
            Result.Success
        }
    }
}
