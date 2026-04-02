package com.yourapp.focusflow.feature_task.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<TaskEntity>
}
