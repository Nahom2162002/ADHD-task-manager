package com.yourapp.focusflow.core.system

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Process
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageTracker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    /**
     * Checks if the app has the required PACKAGE_USAGE_STATS permission.
     * This permission must be granted manually by the user in Settings.
     */
    fun hasUsageStatsPermission(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.noteOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    /**
     * Returns the package name of the app currently in the foreground.
     * Note: This is a "snapshot" and works best when polled or called
     * during a Focus Session to check for distractions.
     */
    fun getForegroundApp(): String? {
        val endTime = System.currentTimeMillis()
        val startTime = endTime - 1000 * 60 // Look at the last minute

        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        return stats?.maxByOrNull { it.lastTimeUsed }?.packageName
    }

    /**
     * Gets the total time spent (in milliseconds) on a specific app 
     * within a given time range.
     */
    fun getTimeSpentOnApp(packageName: String, startTime: Long, endTime: Long): Long {
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )
        
        return stats?.find { it.packageName == packageName }?.totalTimeInForeground ?: 0L
    }
}
