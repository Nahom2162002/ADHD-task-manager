package com.yourapp.focusflow.feature_focus.presentation

import androidx.lifecycle.ViewModel
import com.yourapp.focusflow.feature_focus.domain.usecase.StartFocusSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val startFocusSession: StartFocusSession,
) : ViewModel()
