package com.yourapp.focusflow.feature_task.data.repository

import com.yourapp.focusflow.feature_task.data.local.TaskDao
import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {
    override suspend fun getTasks(): List<Task> =
        taskDao.getAll().map { entity ->
            Task(id = entity.id, title = entity.title, isCompleted = entity.isCompleted)
        }

    override suspend fun addTask(task: Task) {
        // TODO: map + insert
    }

    override suspend fun completeTask(id: String) {
        // TODO: update entity
    }
}
