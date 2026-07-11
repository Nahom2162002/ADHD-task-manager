package com.yourapp.focusflow.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import com.yourapp.focusflow.core.system.FocusModeManager
import com.yourapp.focusflow.core.util.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@HiltWorker
class FocusWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val focusModeManager: FocusModeManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val durationMins = inputData.getInt("DURATION_MINS", 25)
        val durationMillis = TimeUnit.MINUTES.toMillis(durationMins.toLong())
        
        setForeground(createForegroundInfo(durationMins))

        // Wait for the focus duration to end
        delay(durationMillis)

        // End the session in the system layer
        focusModeManager.stopFocusSession()

        return Result.success()
    }

    private fun createForegroundInfo(duration: Int): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, Constants.FOCUS_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Focus Session Active")
            .setContentText("Stay focused! $duration minutes to go.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .build()

        return ForegroundInfo(Constants.FOCUS_NOTIFICATION_ID, notification)
    }
}
