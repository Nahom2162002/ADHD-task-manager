package com.yourapp.focusflow.feature_focus.domain.repository

interface FocusRepository {
    suspend fun startSession()
    suspend fun endSession()
    suspend fun blockApps(packageNames: List<String>)
}
