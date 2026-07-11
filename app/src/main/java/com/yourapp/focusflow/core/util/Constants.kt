package com.yourapp.focusflow.core.util

/**
 * App-wide constants for FocusFlow.
 * Centralizing these values makes it easier to tune the ADHD-specific logic 
 * (like task limits) without hunting through multiple files.
 */
object Constants {
    const val APP_NAME: String = "FocusFlow"

    // Focus & Break Durations (in minutes)
    const val DEFAULT_FOCUS_DURATION_MINS = 25
    const val SHORT_BREAK_DURATION_MINS = 5
    const val LONG_BREAK_DURATION_MINS = 15
    
    // Focus Intervals
    const val POMODORO_SESSIONS_UNTIL_LONG_BREAK = 4

    // Task Limits
    // Limiting active tasks is a key strategy for ADHD to prevent choice paralysis.
    const val MAX_ACTIVE_TASKS = 5
    const val RECOMMENDED_DAILY_TASKS = 3

    // Notification IDs & Channels
    const val FOCUS_NOTIFICATION_CHANNEL_ID = "focus_channel"
    const val BREAK_NOTIFICATION_CHANNEL_ID = "break_channel"
    const val FOCUS_NOTIFICATION_ID = 1001
    const val BREAK_NOTIFICATION_ID = 1002

    const val GEMINI_API_KEY = BuildConfig.GEMINI_API_KEY
}
