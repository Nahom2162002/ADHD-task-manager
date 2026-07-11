package com.yourapp.focusflow.feature_schedule.domain.usecase

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

data class ScheduledItem(
    val task: Task?,
    val isBreak: Boolean = false,
    val durationMins: Int,
    val startTimeDisplay: String
)

package com.yourapp.focusflow.feature_schedule.domain.usecase

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import com.yourapp.focusflow.feature_schedule.domain.repository.ScheduleAiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GenerateSchedule @Inject constructor(
    private val taskRepository: TaskRepository,
    private val aiService: ScheduleAiService // Injected AI service
) {
    suspend operator fun invoke(): List<ScheduledItem> {
        val tasks = taskRepository.getTasks().first().filter { !it.isCompleted }

        return try {
            // Attempt to get a "Smart Plan" from the AI
            val aiSchedule = aiService.prioritizeTasks(tasks)
            if (aiSchedule.isNotEmpty()) aiSchedule else generateLocalSchedule(tasks)
        } catch (e: Exception) {
            // Fallback to local logic if AI fails
            generateLocalSchedule(tasks)
        }
    }

    private fun generateLocalSchedule(tasks: List<Task>): List<ScheduledItem> {
        // Your existing logic here...
        return emptyList()
    }
}
