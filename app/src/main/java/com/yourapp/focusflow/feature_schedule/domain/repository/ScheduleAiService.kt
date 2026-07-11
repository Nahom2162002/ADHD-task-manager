package com.yourapp.focusflow.feature_schedule.domain.repository

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_schedule.domain.usecase.ScheduledItem

interface ScheduleAiService {
    /**
     * Uses AI to organize tasks into a realistic, ADHD-friendly schedule.
     */
    suspend fun prioritizeTasks(tasks: List<Task>): List<ScheduledItem>
}
