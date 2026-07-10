package com.yourapp.focusflow.feature_focus.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.focusflow.core.util.Constants
import com.yourapp.focusflow.core.util.TimeUtils
import com.yourapp.focusflow.feature_focus.domain.repository.FocusRepository
import com.yourapp.focusflow.feature_focus.domain.usecase.EndFocusSession
import com.yourapp.focusflow.feature_focus.domain.usecase.StartFocusSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val repository: FocusRepository,
    private val startFocusSessionUseCase: StartFocusSession,
    private val endFocusSessionUseCase: EndFocusSession
) : ViewModel() {

    val isFocusActive = repository.isFocusActive

    private val _timerDisplay = MutableStateFlow("25:00")
    val timerDisplay: StateFlow<String> = _timerDisplay.asStateFlow()

    private val _progress = MutableStateFlow(1f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private var timerJob: Job? = null
    private var totalSeconds = Constants.DEFAULT_FOCUS_DURATION_MINS * 60L
    private var secondsRemaining = totalSeconds

    fun toggleFocus() {
        viewModelScope.launch {
            if (isFocusActive.value) {
                stopFocus()
            } else {
                startFocus()
            }
        }
    }

    private suspend fun startFocus() {
        // In a real app, we'd get blocked apps from user settings
        startFocusSessionUseCase(Constants.DEFAULT_FOCUS_DURATION_MINS, emptySet())
        startTimer()
    }

    private suspend fun stopFocus() {
        endFocusSessionUseCase()
        stopTimer()
        resetTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (secondsRemaining > 0) {
                delay(1000)
                secondsRemaining--
                updateTimerUi()
            }
            stopFocus() // Auto-stop when finished
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        secondsRemaining = totalSeconds
        updateTimerUi()
    }

    private fun updateTimerUi() {
        _timerDisplay.value = TimeUtils.formatSecondsToDisplayTime(secondsRemaining)
        _progress.value = TimeUtils.calculateProgress(secondsRemaining, totalSeconds)
    }
}
