package com.yourapp.focusflow.core.system

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.yourapp.focusflow.R
import com.yourapp.focusflow.core.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val notificationManager = 
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val focusChannel = NotificationChannel(
                Constants.FOCUS_NOTIFICATION_CHANNEL_ID,
                "Focus Sessions",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for active focus sessions"
            }
            notificationManager.createNotificationChannel(focusChannel)
        }
    }

    fun showReminder(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, Constants.FOCUS_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your app icon later
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Constants.FOCUS_NOTIFICATION_ID, notification)
    }
}
