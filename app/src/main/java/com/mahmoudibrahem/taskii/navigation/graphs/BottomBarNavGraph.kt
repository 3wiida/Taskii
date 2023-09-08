package com.mahmoudibrahem.taskii.navigation.graphs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mahmoudibrahem.taskii.navigation.screens.HomeScreens
import com.mahmoudibrahem.taskii.navigation.screens.OnboardingScreens
import com.mahmoudibrahem.taskii.ui.screens.home.HomeScreen
import com.mahmoudibrahem.taskii.ui.screens.search.SearchScreen
import com.mahmoudibrahem.taskii.util.Constants.BOTTOM_BAR_GRAPH_ROUTE

fun NavGraphBuilder.bottomBarNavGraph(navController: NavHostController) {
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
    }
}