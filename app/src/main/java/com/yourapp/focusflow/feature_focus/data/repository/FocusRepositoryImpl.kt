package com.yourapp.focusflow.feature_focus.data.repository

import com.yourapp.focusflow.core.system.FocusModeManager
import com.yourapp.focusflow.feature_focus.domain.repository.FocusRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusRepositoryImpl @Inject constructor(
    private val focusModeManager: FocusModeManager
) : FocusRepository {

    override val isFocusActive: StateFlow<Boolean> = focusModeManager.isFocusActive

    override suspend fun startSession(durationMins: Int, blockedApps: Set<String>) {
        focusModeManager.startFocusSession(durationMins, blockedApps)
    }

    override suspend fun endSession() {
        focusModeManager.stopFocusSession()
    }
}
