package com.mahmoudibrahem.taskii.ui.screens.user_naming

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.ui.theme.AppMainColor
import com.mahmoudibrahem.taskii.ui.theme.SfDisplay
import com.mahmoudibrahem.taskii.ui.theme.TextFieldColor
import com.mahmoudibrahem.taskii.util.Constants.BOTTOM_BAR_GRAPH_ROUTE
import com.mahmoudibrahem.taskii.util.Constants.ONBOARDING_GRAPH_ROUTE

@Composable
fun UserNamingScreen(
    viewModel: UserNamingViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                top = 40.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
            .scrollable(state = rememberScrollableState { it }, orientation = Orientation.Vertical)
    ) {
        var name by remember { mutableStateOf("") }
        val buttonColor = animateColorAsState(
            targetValue = if (name.length > 2) AppMainColor else Color.LightGray,
            animationSpec = tween(1000, easing = LinearEasing),
            label = ""
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.taskii_icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp),
                alignment = Alignment.TopStart
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.welcome_to_taskii),
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(R.string.naming_screen_body),
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF52465F),
                modifier = Modifier.padding(bottom = 48.dp),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = name,
                onValueChange = { newName -> name = newName },
                textStyle = TextStyle(fontFamily = SfDisplay),
                placeholder = { Text(text = "Tap to enter your name", fontFamily = SfDisplay) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = TextFieldColor,
                    unfocusedBorderColor = TextFieldColor,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedContainerColor = TextFieldColor,
                    unfocusedContainerColor = TextFieldColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .clip(RoundedCornerShape(16.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor.value),
            enabled = name.length > 3,
            onClick = {
                viewModel.saveUsername(name = name)
                viewModel.saveOnboardingState()
                navController.navigate(route = BOTTOM_BAR_GRAPH_ROUTE) {
                    popUpTo(route = ONBOARDING_GRAPH_ROUTE) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(
                text = "CONFIRM",
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}