package com.yourapp.focusflow.feature_schedule.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.yourapp.focusflow.core.util.Constants
import com.yourapp.focusflow.feature_schedule.domain.repository.ScheduleAiService
import com.yourapp.focusflow.feature_schedule.domain.usecase.ScheduledItem
import com.yourapp.focusflow.feature_task.domain.model.Task
import org.json.JSONArray
import javax.inject.Inject

/**
 * Implementation of ScheduleAiService using Google's Gemini.
 * This service takes a list of tasks and asks the AI to return a JSON 
 * structure optimized for ADHD workflows.
 */
class GeminiScheduleService @Inject constructor() : ScheduleAiService {

    override suspend fun prioritizeTasks(tasks: List<Task>): List<ScheduledItem> {
        // Construct the prompt with Task IDs and Titles for mapping later
        val tasksContext = tasks.joinToString("\n") { "- ${it.title} (ID: ${it.id}) (Priority: ${it.priority})" }

        val model = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = Constants.GEMINI_API_KEY
        )
        
        val prompt = """
            You are an ADHD Productivity Coach. Your goal is to create a schedule that 
            minimizes cognitive overwhelm and accounts for "Time Blindness."
            
            Tasks to organize:
            $tasksContext
            
            ADHD Rules for Scheduling:
            1. "Eat the Frog": Put the most complex or high-priority task first.
            2. "Transition Tax": Every time a user switches tasks, add a 5-10 minute buffer. 
               Use 10 minutes if the next task is high priority.
            3. "Variety": Do not schedule more than two "Deep Work" sessions in a row.
            4. "Pomodoro": Work sessions are 25 minutes.
            5. "Coach Tip": Provide a short (max 10 words) encouraging tip for each item.
            
            Return a JSON array ONLY:
            [
              { "id": "task_id", "title": "Task Title", "is_break": false, "duration": 25, "time": "09:00", "advice": "Just start for 2 minutes, that's the hardest part" },
              { "id": "break", "title": "Transition Buffer", "is_break": true, "duration": 10, "time": "09:25", "advice": "Stretch your legs and grab some water" }
            ]
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt).text ?: return emptyList()
            parseAiResponse(response, tasks)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun parseAiResponse(jsonString: String, originalTasks: List<Task>): List<ScheduledItem> {
        val result = mutableListOf<ScheduledItem>()
        try {
            // Clean the response in case AI included markdown blocks
            val cleanedJson = if (jsonString.contains("[")) {
                jsonString.substring(jsonString.indexOf("["), jsonString.lastIndexOf("]") + 1)
            } else {
                jsonString
            }
            
            val jsonArray = JSONArray(cleanedJson)
            val taskMap = originalTasks.associateBy { it.id }

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.getString("id")
                val isBreak = obj.getBoolean("is_break")
                val duration = obj.getInt("duration")
                val startTime = obj.getString("time")
                val advice = obj.optString("advice", "")

                result.add(
                    ScheduledItem(
                        task = if (isBreak) null else taskMap[id],
                        isBreak = isBreak,
                        durationMins = duration,
                        startTimeDisplay = startTime,
                        coachTip = advice
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
