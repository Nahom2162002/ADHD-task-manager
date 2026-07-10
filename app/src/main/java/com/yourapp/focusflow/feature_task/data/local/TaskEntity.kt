package com.yourapp.focusflow.feature_task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Int = 1, // 1: Low, 2: Medium, 3: High
    val createdAt: Long = System.currentTimeMillis()
)
