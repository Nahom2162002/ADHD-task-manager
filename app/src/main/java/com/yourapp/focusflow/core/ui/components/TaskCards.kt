package com.yourapp.focusflow.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourapp.focusflow.feature_task.domain.model.Task

@Composable
fun TaskCard(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onToggleCompletion: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = { onTaskClick(task) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleCompletion(task) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    TaskCard(
        task = Task(id = "1", title = "Finish Jetpack Compose UI", isCompleted = false),
        onTaskClick = {},
        onToggleCompletion = {}
    )
}
