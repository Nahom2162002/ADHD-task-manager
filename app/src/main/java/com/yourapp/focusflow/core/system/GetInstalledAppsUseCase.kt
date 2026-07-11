package com.yourapp.focusflow.core.system

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class AppInfo(
    val name: String,
    val packageName: String
)

class GetInstalledAppsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): List<AppInfo> {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        
        return apps.filter { app ->
            // Filter out system apps that are essential (like the launcher or system UI)
            (app.flags and ApplicationInfo.FLAG_SYSTEM) == 0 || 
            (app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
        }.map { app ->
            AppInfo(
                name = app.loadLabel(pm).toString(),
                packageName = app.packageName
            )
        }.sortedBy { it.name }
    }
}
