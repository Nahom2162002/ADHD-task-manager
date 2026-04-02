package com.yourapp.focusflow.feature_focus.data.repository

import com.yourapp.focusflow.feature_focus.domain.repository.FocusRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusRepositoryImpl @Inject constructor() : FocusRepository {
    override suspend fun startSession() {}
    override suspend fun endSession() {}
    override suspend fun blockApps(packageNames: List<String>) {}
}
