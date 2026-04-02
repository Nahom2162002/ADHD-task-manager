package com.yourapp.focusflow.feature_task.domain.usecase

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(): List<Task> = repository.getTasks()
}
