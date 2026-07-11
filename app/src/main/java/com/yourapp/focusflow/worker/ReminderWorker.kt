package com.yourapp.focusflow.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yourapp.focusflow.core.system.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): com.google.android.gms.common.api.Result {
        notificationHelper.showReminder("Checking in! 👋", "Your next task is waiting. Let's keep the flow going!")
        return com.google.android.gms.common.api.Result.success()
    }
}