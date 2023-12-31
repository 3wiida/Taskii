package com.mahmoudibrahem.taskii.navigation.screens

import com.mahmoudibrahem.taskii.util.Constants.ANALYTICS_SCREEN_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.CREATE_TASK_SCREEN_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.HOME_SCREEN_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.NAMING_USER_SCREEN
import com.mahmoudibrahem.taskii.util.Constants.ONBOARDING_SCREEN_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.SEARCH_SCREEN_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.TASK_DETAILS_SCREEN_ROUTE

sealed class OnboardingScreens(val route: String) {
    object OnboardingScreen : OnboardingScreens(route = ONBOARDING_SCREEN_ROUTE)
    object NamingUserScreen : OnboardingScreens(route = NAMING_USER_SCREEN)
}

sealed class HomeScreens(val route: String){
    object Home:HomeScreens(route = HOME_SCREEN_ROUTE)
    object Search:HomeScreens(route = SEARCH_SCREEN_ROUTE)
    object CreateTask:HomeScreens(route = CREATE_TASK_SCREEN_ROUTE)
    object TaskDetails:HomeScreens(route = TASK_DETAILS_SCREEN_ROUTE)
    object Analytics:HomeScreens(route = ANALYTICS_SCREEN_ROUTE)
}
