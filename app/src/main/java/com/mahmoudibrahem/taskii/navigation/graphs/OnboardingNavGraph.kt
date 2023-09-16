package com.mahmoudibrahem.taskii.navigation.graphs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.navigation.screens.OnboardingScreens
import com.mahmoudibrahem.taskii.pojo.OnboardingPage
import com.mahmoudibrahem.taskii.ui.screens.onboarding.OnBoardingScreen
import com.mahmoudibrahem.taskii.ui.screens.user_naming.UserNamingScreen
import com.mahmoudibrahem.taskii.util.Constants.ONBOARDING_GRAPH_ROUTE


@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    navigation(
        route = ONBOARDING_GRAPH_ROUTE,
        startDestination = OnboardingScreens.OnboardingScreen.route
    ) {
        composable(route = OnboardingScreens.OnboardingScreen.route) {
            OnBoardingScreen(
                pages = listOf(
                    OnboardingPage(
                        image = painterResource(
                            id = R.drawable.onboarding_1
                        ),
                        headText = "Hello!",
                        bodyText = "Welcome!!! Do you want clear task super fast with Taskii"
                    ),
                    OnboardingPage(
                        image = painterResource(
                            id = R.drawable.onboarding_2
                        ),
                        headText = "Arrangement",
                        bodyText = "Easily arrange work order for you to easily manage"
                    ),
                    OnboardingPage(
                        image = painterResource(
                            id = R.drawable.onboarding_3
                        ),
                        headText = "Solving",
                        bodyText = "It has never been easier to complete tasks. Get started with us!"
                    )
                ),
                navController = navController
            )
        }
        composable(route=OnboardingScreens.NamingUserScreen.route){
            UserNamingScreen(navController = navController)
        }
    }
}