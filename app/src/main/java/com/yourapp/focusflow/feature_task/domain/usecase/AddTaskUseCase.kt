package com.yourapp.focusflow.feature_task.domain.usecase

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(task: Task) {
        // Here you could add ADHD-specific validation logic, 
        // like checking Constants.MAX_ACTIVE_TASKS before allowing a new one.
        repository.insertTask(task)
    }
}
