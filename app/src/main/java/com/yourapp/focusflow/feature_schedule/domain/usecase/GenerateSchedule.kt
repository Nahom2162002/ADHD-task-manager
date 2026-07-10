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

class GenerateSchedule @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): List<ScheduledItem> {
        val tasks = taskRepository.getTasks().first()
            .filter { !it.isCompleted }
            .sortedByDescending { it.priority }

        val schedule = mutableListOf<ScheduledItem>()
        var currentMinutes = 540 // Start at 9:00 AM (9 * 60)

        tasks.take(5).forEach { task -> // Limit to 5 tasks to avoid overwhelm
            // 1. Add the Task
            schedule.add(
                ScheduledItem(
                    task = task,
                    durationMins = 25,
                    startTimeDisplay = formatMinutesToTime(currentMinutes)
                )
            )
            currentMinutes += 25

            // 2. Add a Mandatory ADHD Transition Buffer (5 mins)
            schedule.add(
                ScheduledItem(
                    task = null,
                    isBreak = true,
                    durationMins = 5,
                    startTimeDisplay = formatMinutesToTime(currentMinutes)
                )
            )
            currentMinutes += 5
        }

        return schedule
    }

    private fun formatMinutesToTime(totalMinutes: Long): String {
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return String.format("%02d:%02d", hours, mins)
    }
}
