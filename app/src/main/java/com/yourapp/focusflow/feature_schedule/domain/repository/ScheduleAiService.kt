package com.yourapp.focusflow.feature_schedule.domain.repository

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_schedule.domain.usecase.ScheduledItem

interface ScheduleAiService {
    suspend fun prioritizeTasks(tasks: List<Task>): List<ScheduledItem>
}