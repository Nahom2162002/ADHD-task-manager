package com.yourapp.focusflow.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.yourapp.focusflow.navigation.FocusFlowNavGraph

@Composable
fun ApplicationNavigation() {
    val navController = rememberNavController()
    FocusFlowNavGraph(navController = navController)
}
