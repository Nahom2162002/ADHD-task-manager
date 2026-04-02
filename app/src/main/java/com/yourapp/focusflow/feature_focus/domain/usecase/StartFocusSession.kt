package com.yourapp.focusflow.feature_focus.domain.usecase

import com.yourapp.focusflow.feature_focus.domain.repository.FocusRepository
import javax.inject.Inject

class StartFocusSession @Inject constructor(
    private val repository: FocusRepository,
) {
    suspend operator fun invoke() {
        repository.startSession()
    }
}
