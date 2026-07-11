package com.yourapp.focusflow.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularTimer(
    timeRemaining: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Background Circle (Track)
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(240.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round,
        )
        
        // Foreground Circle (Progress)
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(240.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round,
        )

        // Time Text
        Text(
            text = timeRemaining,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 48.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CircularTimerPreview() {
    CircularTimer(
        timeRemaining = "25:00",
        progress = 0.8f
    )
}
