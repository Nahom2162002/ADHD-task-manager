package com.yourapp.focusflow.feature_schedule.domain.usecase

import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import com.yourapp.focusflow.feature_schedule.domain.repository.ScheduleAiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

data class ScheduledItem(
    val task: Task?,
    val isBreak: Boolean = false,
    val durationMins: Int,
    val startTimeDisplay: String,
    val coachTip: String? = null
)

/**
 * Handles generating the daily schedule.
 * It attempts to use AI for smart, ADHD-friendly prioritization, 
 * falling back to a local sorting algorithm if the AI is unavailable.
 */
class GenerateSchedule @Inject constructor(
    private val taskRepository: TaskRepository,
    private val aiService: ScheduleAiService
) {
    suspend operator fun invoke(): List<ScheduledItem> {
        val tasks = taskRepository.getTasks().first()
            .filter { !it.isCompleted }

        if (tasks.isEmpty()) return emptyList()

        return try {
            val aiSchedule = aiService.prioritizeTasks(tasks)
            if (aiSchedule.isNotEmpty()) {
                aiSchedule
            } else {
                generateLocalSchedule(tasks)
            }
        } catch (e: Exception) {
            generateLocalSchedule(tasks)
        }
    }

    private fun generateLocalSchedule(tasks: List<Task>): List<ScheduledItem> {
        val sortedTasks = tasks.sortedByDescending { it.priority }
        val schedule = mutableListOf<ScheduledItem>()
        var currentMinutes = 540 // Start at 9:00 AM

        sortedTasks.take(5).forEach { task ->
            schedule.add(
                ScheduledItem(
                    task = task,
                    durationMins = 25,
                    startTimeDisplay = formatMinutesToTime(currentMinutes),
                    coachTip = "Start with your highest priority task to build momentum."
                )
            )
            currentMinutes += 25

            schedule.add(
                ScheduledItem(
                    task = null,
                    isBreak = true,
                    durationMins = 5,
                    startTimeDisplay = formatMinutesToTime(currentMinutes),
                    coachTip = "Take a short break to reset your focus."
                )
            )
            currentMinutes += 5
        }
        return schedule
    }

    private fun formatMinutesToTime(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return String.format("%02d:%02d", hours, mins)
    }
}
