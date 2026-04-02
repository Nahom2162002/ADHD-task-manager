package com.yourapp.focusflow.feature_task.domain.repository

import com.yourapp.focusflow.feature_task.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task)
    suspend fun completeTask(id: String)
}
