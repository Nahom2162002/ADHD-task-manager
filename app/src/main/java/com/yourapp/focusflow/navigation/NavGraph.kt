package com.yourapp.focusflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yourapp.focusflow.feature_achievement.presentation.AchievementScreen
import com.yourapp.focusflow.feature_focus.presentation.FocusScreen
import com.yourapp.focusflow.feature_schedule.presentation.ScheduleScreen
import com.yourapp.focusflow.feature_task.presentation.TaskScreen

@Composable
fun FocusFlowNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Tasks,
        modifier = modifier
    ) {
        composable(Routes.Tasks) { 
            TaskScreen(
                onNavigateToAchievements = { navController.navigate(Routes.Achievements) },
                onNavigateToFocus = { navController.navigate(Routes.Focus) }
            ) 
        }
        composable(Routes.Focus) { 
            FocusScreen(
                onNavigateToAchievements = { navController.navigate(Routes.Achievements) }
            ) 
        }
        composable(Routes.Schedule) { ScheduleScreen() }
        composable(Routes.Achievements) { AchievementScreen() }
    }
}
