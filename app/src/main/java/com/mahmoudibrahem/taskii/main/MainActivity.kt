package com.mahmoudibrahem.taskii.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.navigation.graphs.RootNavGraph
import com.mahmoudibrahem.taskii.pojo.OnboardingPage
import com.mahmoudibrahem.taskii.ui.screens.onboarding.CircularProgressIndicator
import com.mahmoudibrahem.taskii.ui.screens.onboarding.OnBoardingScreen
import com.mahmoudibrahem.taskii.ui.theme.TaskiiTheme
import com.mahmoudibrahem.taskii.util.Constants.BOTTOM_BAR_GRAPH_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.ONBOARDING_GRAPH_ROUTE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        installSplashScreen().setKeepOnScreenCondition { viewModel.isKeepSplash.value }

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = Color.White)
            val isShowOnboarding = viewModel.isShowOnboarding.collectAsState(true).value?:true
            RootNavGraph(
                navController = rememberNavController(),
                startGraph = if (isShowOnboarding) ONBOARDING_GRAPH_ROUTE else BOTTOM_BAR_GRAPH_ROUTE
            )
        }
    }
}

