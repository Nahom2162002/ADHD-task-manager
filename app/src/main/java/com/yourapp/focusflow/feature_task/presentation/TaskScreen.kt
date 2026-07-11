package com.yourapp.focusflow.feature_task.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourapp.focusflow.core.ui.components.TaskCard
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    onNavigateToAchievements: () -> Unit,
    onNavigateToFocus: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val newTaskTitle by viewModel.newTaskTitle.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TaskViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
                is TaskViewModel.UiEvent.ShowAchievement -> {
                    snackbarHostState.showSnackbar(
                        message = "Achievement Unlocked: ${event.message}",
                        actionLabel = "View"
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("FocusFlow Tasks") },
                actions = {
                    IconButton(onClick = onNavigateToFocus) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Focus Mode")
                    }
                    IconButton(onClick = onNavigateToAchievements) {
                        Icon(Icons.Default.Star, contentDescription = "Achievements")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Add Task Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = viewModel::onTaskTitleChanged,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Add a new task...") },
                    singleLine = true
                )
                IconButton(
                    onClick = viewModel::addTask,
                    enabled = newTaskTitle.isNotBlank()
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Task List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskCard(
                        task = task,
                        onTaskClick = { /* Handle details */ },
                        onToggleCompletion = { viewModel.onToggleTaskCompletion(it) }
                    )
                }
            }
        }
    }
}
