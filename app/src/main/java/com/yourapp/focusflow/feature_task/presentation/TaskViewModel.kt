package com.yourapp.focusflow.feature_task.presentation

import androidx.lifecycle.ViewModel
import com.yourapp.focusflow.feature_task.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasks: GetTasksUseCase,
) : ViewModel()
