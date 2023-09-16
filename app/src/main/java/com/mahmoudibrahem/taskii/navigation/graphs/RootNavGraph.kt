package com.mahmoudibrahem.taskii.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mahmoudibrahem.taskii.util.Constants.ROOT_GRAPH_ROUTE

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startGraph: String
) {
    NavHost(
        navController = navController,
        startDestination = startGraph,
        route = ROOT_GRAPH_ROUTE
    ) {
        onboardingNavGraph(navController = navController)
        mainNavGraph(navController = navController)
    }
}