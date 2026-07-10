package com.yourapp.focusflow.feature_focus.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface FocusRepository {
    val isFocusActive: StateFlow<Boolean>
    suspend fun startSession(durationMins: Int, blockedApps: Set<String>)
    suspend fun endSession()
}
