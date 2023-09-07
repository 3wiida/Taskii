package com.mahmoudibrahem.taskii.ui.screens.onboarding

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.navigation.screens.OnboardingScreens
import com.mahmoudibrahem.taskii.pojo.OnboardingPage
import com.mahmoudibrahem.taskii.ui.theme.SfDisplay
import com.mahmoudibrahem.taskii.ui.theme.AppMainColor
import com.mahmoudibrahem.taskii.ui.theme.AppSecondaryColor
import com.mahmoudibrahem.taskii.ui.theme.SfText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    pages: List<OnboardingPage>,
    navController: NavController,
    pagerState: PagerState = rememberPagerState { pages.size },
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 28.dp,
                end = 28.dp,
                bottom = 56.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnboardingHeader(
            pagerState = pagerState,
            navController = navController
        )
        OnboardingPager(
            pages = pages,
            pagerState = pagerState
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
            activeColor = AppSecondaryColor,
            inactiveColor = Color.LightGray,
        )
        OnboardingFooter(
            pagerState = pagerState,
            navController = navController
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingHeader(
    pagerState: PagerState,
    navController: NavController,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val skipButtonScale by animateFloatAsState(
        targetValue = if (pagerState.currentPage == 2) 0f else 1f,
        animationSpec = tween(500),
        label = ""
    )
    val backButtonScale by animateFloatAsState(
        targetValue = if (pagerState.currentPage == 0) 0f else 1f,
        animationSpec = tween(500),
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .scale(backButtonScale)
                .background(
                    color = AppMainColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .align(Alignment.CenterStart)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    scope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_ic),
                contentDescription = "back",
                tint = Color.White
            )
        }

        ClickableText(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .scale(skipButtonScale),
            text = AnnotatedString("Skip"),
            onClick = {
                navController.navigate(route = OnboardingScreens.NamingUserScreen.route) {
                    popUpTo(OnboardingScreens.OnboardingScreen.route) {
                        inclusive = true
                    }
                }
            },
            style = TextStyle(
                color = AppMainColor,
                fontSize = 20.sp,
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Normal
            )
        )

    }
}


@Composable
fun OnboardingPage(
    onboardingPage: OnboardingPage
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = onboardingPage.image,
            contentDescription = "onboarding Image",
            modifier = Modifier.size(320.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onboardingPage.headText,
            fontSize = 28.sp,
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onboardingPage.bodyText,
            fontSize = 16.sp,
            fontFamily = SfText,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPager(
    pages: List<OnboardingPage>,
    pagerState: PagerState
) {
    HorizontalPager(state = pagerState) { index ->
        OnboardingPage(onboardingPage = pages[index])
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularProgressIndicator(
    pagerState: PagerState,
    circleIndicatorScale: Float,
    arcProgress: Float
) {

    /*val arcProgress by animateFloatAsState(
        targetValue = (360f * (pagerState.currentPage + 1) / 3),
        animationSpec = tween(
            durationMillis = 500
        ),
        label = ""
    )*/


    Box(
        modifier = Modifier
            .size(80.dp)
            .scale(circleIndicatorScale)
            .drawBehind {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 5f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = AppMainColor,
                    startAngle = -90f,
                    sweepAngle = arcProgress,
                    useCenter = false,
                    style = Stroke(width = 5f, cap = StrokeCap.Round)
                )
            }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = (pagerState.currentPage + 1).toString(),
            color = Color.White,
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(
                    AppMainColor,
                    CircleShape
                )
                .size(48.dp)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingFooter(
    pagerState: PagerState,
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        val transition = updateTransition(targetState = pagerState.currentPage, label = "")

        val buttonScale = transition.animateFloat(
            label = "",
            transitionSpec = {
                tween(
                    durationMillis = 250,
                    delayMillis = if (this.targetState == 2) 750 else 0
                )
            }
        ) { currentPage ->
            if (currentPage == 2) 1f else 0f
        }

        val arcProgress = transition.animateFloat(
            label = "",
            transitionSpec = {
                tween(durationMillis = 500)
            }
        ) { currentPage ->
            (360f * (currentPage + 1) / 3)
        }

        val circularIndicatorScale = transition.animateFloat(
            label = "",
            transitionSpec = {
                tween(
                    durationMillis = 250,
                    delayMillis = if (this.targetState == 2) 500 else 250
                )
            }
        ) { currentPage ->
            if (currentPage == 2) 0f else 1f
        }

        CircularProgressIndicator(
            pagerState = pagerState,
            circleIndicatorScale = circularIndicatorScale.value,
            arcProgress = arcProgress.value
        )

        Button(
            onClick = {
                navController.navigate(route = OnboardingScreens.NamingUserScreen.route) {
                    popUpTo(OnboardingScreens.OnboardingScreen.route) {
                        inclusive = true
                    }

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .scale(buttonScale.value),
            colors = ButtonDefaults.buttonColors(backgroundColor = AppMainColor)
        ) {
            Text(
                text = "GET STARTED",
                color = Color.White,
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}