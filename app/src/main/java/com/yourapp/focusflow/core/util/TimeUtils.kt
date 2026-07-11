package com.yourapp.focusflow.core.util

import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeUtils {

    /**
     * Formats seconds into a displayable string (MM:SS or H:MM:SS).
     * Useful for the CircularTimer component.
     */
    fun formatSecondsToDisplayTime(totalSeconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(totalSeconds)
        val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
        val seconds = totalSeconds % 60

        return if (hours > 0) {
            String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }

    /**
     * Calculates the progress ratio (0.0 to 1.0) for a timer.
     */
    fun calculateProgress(remainingSeconds: Long, totalSeconds: Long): Float {
        if (totalSeconds <= 0) return 0f
        return remainingSeconds.toFloat() / totalSeconds.toFloat()
    }

    /**
     * Calculates break intervals based on a total focus duration.
     * Returns a list of timestamps (in seconds) when breaks should occur.
     */
    fun calculateBreakIntervals(totalMinutes: Int, intervalMinutes: Int = 25): List<Long> {
        val intervals = mutableListOf<Long>()
        var current = intervalMinutes.toLong()
        
        while (current < totalMinutes) {
            intervals.add(TimeUnit.MINUTES.toSeconds(current))
            current += intervalMinutes
        }
        
        return intervals
    }

    /**
     * Converts minutes to milliseconds.
     */
    fun minutesToMillis(minutes: Int): Long {
        return TimeUnit.MINUTES.toMillis(minutes.toLong())
    }

    /**
     * Converts seconds to milliseconds.
     */
    fun secondsToMillis(seconds: Long): Long {
        return TimeUnit.SECONDS.toMillis(seconds)
    }
}
