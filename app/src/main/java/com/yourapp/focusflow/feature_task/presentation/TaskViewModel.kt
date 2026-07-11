package com.yourapp.focusflow.feature_task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.focusflow.feature_task.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase
) : ViewModel() {

    val tasks = getTasksUseCase().stateIn(viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), emptyList())

    private val _eventFlow = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onToggleTaskCompletion(taskId: String) {
        viewModelScope.launch {
            val result = completeTaskUseCase(taskId)
            result?.let {
                _eventFlow.emit(UiEvent.ShowReward(it.affirmation, it.unlockedAchievementId))
            }
        }
    }

    sealed class UiEvent {
        data class ShowReward(val message: String, val achievementId: String?) : UiEvent()
    }
}