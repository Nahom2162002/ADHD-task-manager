package com.yourapp.focusflow.feature_focus.domain.usecase

import com.yourapp.focusflow.feature_focus.domain.repository.FocusRepository
import javax.inject.Inject

/**
 * Updates the list of blocked apps.
 * This can be used to dynamically change what's blocked even during a session.
 */
class BlockAppsUseCase @Inject constructor(
    private val repository: FocusRepository
) {
    suspend operator fun invoke(packageNames: Set<String>, durationMins: Int) {
        // If a session is already active, this restarts it with the new list
        repository.startSession(durationMins, packageNames)
    }
}
