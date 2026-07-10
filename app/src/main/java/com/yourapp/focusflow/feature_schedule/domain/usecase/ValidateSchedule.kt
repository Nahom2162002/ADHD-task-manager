package com.yourapp.focusflow.feature_schedule.domain.usecase

import javax.inject.Inject

/**
 * Validates the generated schedule to ensure it follows ADHD-friendly rules.
 * For example: ensuring there aren't too many high-priority tasks in a row.
 */
class ValidateSchedule @Inject constructor() {
    
    operator fun invoke(items: List<ScheduledItem>): Boolean {
        if (items.isEmpty()) return false
        
        // Rule: Avoid more than 3 high-priority tasks without a longer break
        var consecutiveHighPriority = 0
        for (item in items) {
            if (item.task != null && item.task.priority >= 3) {
                consecutiveHighPriority++
            } else if (item.isBreak && item.durationMins >= 15) {
                consecutiveHighPriority = 0
            }
            
            if (consecutiveHighPriority > 3) return false
        }
        
        return true
    }
}
