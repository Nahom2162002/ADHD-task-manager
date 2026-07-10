package com.yourapp.focusflow.feature_task.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Int = 1,
    val createdAt: Long = System.currentTimeMillis()
)
