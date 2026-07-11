package com.yourapp.focusflow.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.privacysandbox.tools.core.generator.build
import com.yourapp.focusflow.core.system.NotificationHelper
import com.yourapp.focusflow.core.util.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@HiltWorker
class BreakWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val durationMins = inputData.getInt("BREAK_DURATION_MINS", 5)
        val durationMillis = TimeUnit.MINUTES.toMillis(durationMins.toLong())
        
        // Show a foreground notification for the break to prevent system kill
        setForeground(createForegroundInfo(durationMins))

        delay(durationMillis)

        // When break is over, nudge them back to focus
        notificationHelper.showReminder(
            "Break Over! ☕", 
            "Time to get back into the flow. Ready to start your next session?"
        )

        return Result.success()
    override suspend fun doWork(): com.google.android.gms.common.api.Result {
        val durationMins = inputData.getInt("BREAK_DURATION", 5)

        // Show a persistent notification during the break
        setForeground(createForegroundInfo(durationMins))

        delay(TimeUnit.MINUTES.toMillis(durationMins.toLong()))

        notificationHelper.showReminder("Break Over!", "Ready to dive back in?")

        return com.google.android.gms.common.api.Result.success()
    }

    private fun createForegroundInfo(duration: Int): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, Constants.BREAK_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Relaxing Break")
            .setContentText("$duration minutes to recharge.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .build()

        return ForegroundInfo(Constants.BREAK_NOTIFICATION_ID, notification)
    }
}
        return ForegroundInfo(Constants.BREAK_NOTIFICATION_ID, notification)
    }
}
