package com.yourapp.focusflow.feature_schedule.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.focusflow.feature_schedule.domain.usecase.GenerateSchedule
import com.yourapp.focusflow.feature_schedule.domain.usecase.ScheduledItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val generateScheduleUseCase: GenerateSchedule
) : ViewModel() {

    private val _scheduleItems = MutableStateFlow<List<ScheduledItem>>(emptyList())
    val scheduleItems: StateFlow<List<ScheduledItem>> = _scheduleItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun generateSchedule() {
        viewModelScope.launch {
            _isLoading.value = true
            _scheduleItems.value = generateScheduleUseCase()
            _isLoading.value = false
        }
    }
}
