package com.yourapp.focusflow.feature_task.domain.repository

import com.yourapp.focusflow.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
}
