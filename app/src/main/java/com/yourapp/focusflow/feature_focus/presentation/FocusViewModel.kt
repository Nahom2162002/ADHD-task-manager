package com.yourapp.focusflow.feature_focus.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.focusflow.core.system.AppInfo
import com.yourapp.focusflow.core.system.GetInstalledAppsUseCase
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
    private val endFocusSessionUseCase: EndFocusSession,
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase
) : ViewModel() {

    val isFocusActive = repository.isFocusActive

    private val _timerDisplay = MutableStateFlow("25:00")
    val timerDisplay: StateFlow<String> = _timerDisplay.asStateFlow()

    private val _progress = MutableStateFlow(1f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps: StateFlow<List<AppInfo>> = _installedApps.asStateFlow()

    private val _selectedApps = MutableStateFlow<Set<String>>(emptySet())
    val selectedApps: StateFlow<Set<String>> = _selectedApps.asStateFlow()

    private var timerJob: Job? = null
    private var totalSeconds = Constants.DEFAULT_FOCUS_DURATION_MINS * 60L
    private var secondsRemaining = totalSeconds

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            _installedApps.value = getInstalledAppsUseCase()
        }
    }

    fun onAppSelectionToggle(packageName: String) {
        val current = _selectedApps.value.toMutableSet()
        if (current.contains(packageName)) {
            current.remove(packageName)
        } else {
            current.add(packageName)
        }
        _selectedApps.value = current
    }

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
        startFocusSessionUseCase(Constants.DEFAULT_FOCUS_DURATION_MINS, _selectedApps.value)
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
            stopFocus()
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
