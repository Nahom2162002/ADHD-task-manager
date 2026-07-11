package com.yourapp.focusflow.app

import android.app.Application
import androidx.privacysandbox.tools.core.generator.build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FocusFlowApp : Application(), androidx.work.Configuration.Provider {
    @Inject lateinit var workerFactory: androidx.hilt.work.HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        androidx.work.Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
