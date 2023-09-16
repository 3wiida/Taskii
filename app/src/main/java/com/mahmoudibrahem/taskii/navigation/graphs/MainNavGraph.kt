package com.mahmoudibrahem.taskii.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mahmoudibrahem.taskii.navigation.screens.HomeScreens
import com.mahmoudibrahem.taskii.ui.screens.analytics.AnalyticsScreen
import com.mahmoudibrahem.taskii.ui.screens.create_task.CreateTaskScreen
import com.mahmoudibrahem.taskii.ui.screens.home.HomeScreen
import com.mahmoudibrahem.taskii.ui.screens.search.SearchScreen
import com.mahmoudibrahem.taskii.ui.screens.task_details.TaskDetailsScreen
import com.mahmoudibrahem.taskii.util.Constants.BOTTOM_BAR_GRAPH_ROUTE

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        route = BOTTOM_BAR_GRAPH_ROUTE,
        startDestination = HomeScreens.Home.route
    ) {
        composable(route = HomeScreens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = HomeScreens.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(route = HomeScreens.CreateTask.route) {
            CreateTaskScreen(navController = navController)
        }
        composable(route = HomeScreens.TaskDetails.route) { backStackEntry ->
            val taskId=backStackEntry.arguments?.getString("task_id")?.toInt()
            if (taskId != null) {
                TaskDetailsScreen(navController = navController, taskId = taskId)
            }
        }
        composable(route=HomeScreens.Analytics.route){
            AnalyticsScreen(navController = navController)
        }
    }
}