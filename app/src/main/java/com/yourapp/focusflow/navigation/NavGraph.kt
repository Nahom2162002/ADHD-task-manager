package com.yourapp.focusflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yourapp.focusflow.feature_achievement.presentation.AchievementScreen
import com.yourapp.focusflow.feature_focus.presentation.FocusScreen
import com.yourapp.focusflow.feature_schedule.presentation.ScheduleScreen
import com.yourapp.focusflow.feature_task.presentation.TaskScreen

@Composable
fun FocusFlowNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Tasks,
    ) {
        composable(Routes.Tasks) { TaskScreen() }
        composable(Routes.Focus) { FocusScreen() }
        composable(Routes.Schedule) { ScheduleScreen() }
        composable(Routes.Achievements) { AchievementScreen() }
    }
}
