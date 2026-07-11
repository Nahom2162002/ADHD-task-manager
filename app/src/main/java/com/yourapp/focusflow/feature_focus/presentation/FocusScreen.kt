package com.yourapp.focusflow.feature_focus.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourapp.focusflow.core.ui.components.CircularTimer
import com.yourapp.focusflow.core.ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusScreen(
    viewModel: FocusViewModel = hiltViewModel()
) {
    val isFocusActive by viewModel.isFocusActive.collectAsState()
    val timerDisplay by viewModel.timerDisplay.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val installedApps by viewModel.installedApps.collectAsState()
    val selectedApps by viewModel.selectedApps.collectAsState()
    
    var showAppSelection by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Focus Session") },
                actions = {
                    if (!isFocusActive) {
                        IconButton(onClick = { showAppSelection = true }) {
                            Icon(Icons.Default.Settings, contentDescription = "Select Apps to Block")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            CircularTimer(
                timeRemaining = timerDisplay,
                progress = progress,
                modifier = Modifier.size(280.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            PrimaryButton(
                text = if (isFocusActive) "Stop Focus" else "Start Focus",
                onClick = { viewModel.toggleFocus() },
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            if (isFocusActive) {
                Text(
                    text = "Distractions are being blocked. Stay focused!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                Text(
                    text = "${selectedApps.size} apps selected to block",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        if (showAppSelection) {
            ModalBottomSheet(
                onDismissRequest = { showAppSelection = false }
            ) {
                Column(modifier = Modifier.fillMaxHeight(0.8f)) {
                    Text(
                        "Select Apps to Block",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(installedApps) { app ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = selectedApps.contains(app.packageName),
                                    onCheckedChange = { viewModel.onAppSelectionToggle(app.packageName) }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(app.name)
                            }
                        }
                    }
                }
            }
        }
    }
}
