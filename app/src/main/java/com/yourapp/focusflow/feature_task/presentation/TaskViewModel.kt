package com.yourapp.focusflow.feature_task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.focusflow.feature_task.domain.model.Task
import com.yourapp.focusflow.feature_task.domain.usecase.AddTaskUseCase
import com.yourapp.focusflow.feature_task.domain.usecase.CompleteTaskUseCase
import com.yourapp.focusflow.feature_task.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = getTasksUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _newTaskTitle = MutableStateFlow("")
    val newTaskTitle: StateFlow<String> = _newTaskTitle.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onTaskTitleChanged(newTitle: String) {
        _newTaskTitle.value = newTitle
    }

    fun addTask() {
        val title = _newTaskTitle.value.trim()
        if (title.isNotEmpty()) {
            viewModelScope.launch {
                val newTask = Task(
                    id = UUID.randomUUID().toString(),
                    title = title
                )
                addTaskUseCase(newTask)
                _newTaskTitle.value = ""
            }
        }
    }

    fun onToggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val affirmation = completeTaskUseCase(task.id)
            if (affirmation != null) {
                _eventFlow.emit(UiEvent.ShowSnackbar(affirmation))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
    }
}
