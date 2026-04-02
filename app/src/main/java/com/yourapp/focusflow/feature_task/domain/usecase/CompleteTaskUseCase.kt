package com.yourapp.focusflow.feature_task.domain.usecase

import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(id: String) {
        repository.completeTask(id)
    }
}
