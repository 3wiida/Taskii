package com.mahmoudibrahem.taskii.ui.screens.create_task

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.ui.theme.AppMainColor
import com.mahmoudibrahem.taskii.ui.theme.AppSecondaryColor
import com.mahmoudibrahem.taskii.ui.theme.SfDisplay
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    navController: NavController,
    viewModel: CreateTaskViewModel = hiltViewModel()
) {
    val selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val calendarState = rememberUseCaseState()
    val clockState = rememberUseCaseState()
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    val checkList = remember { mutableStateListOf<String>() }
    val buttonColor = animateColorAsState(
        targetValue = if (taskName.isNotEmpty() && taskDescription.isNotEmpty() && date.isNotEmpty()) AppMainColor else Color.LightGray,
        animationSpec = tween(1000, easing = LinearEasing),
        label = ""
    )

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(yearSelection = true, monthSelection = true),
        selection = CalendarSelection.Date {
            date = it.toString()
        }
    )

    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            time = "$hours:$minutes"
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CreateTaskHeader(
                onCancelClicked = { navController.navigateUp() },
                onResetClicked = {
                    taskName = ""
                    taskDescription = ""
                    date = ""
                    time = ""
                    checkList.clear()
                },
                isResetEnabled = taskName.isNotEmpty() || taskDescription.isNotEmpty() || date.isNotEmpty() || checkList.isNotEmpty()
            )
            Spacer(modifier = Modifier.height(16.dp))
            DataEntrySection(
                taskName = taskName,
                onTaskNameChanged = { taskName = it },
                taskDescription = taskDescription,
                onTaskDescriptionChanged = { taskDescription = it },
                date = date,
                checkList = checkList,
                calenderState = calendarState,
                clockState = clockState,
                time = time,
                scope = rememberCoroutineScope()
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor.value),
            enabled = taskName.isNotEmpty() && taskDescription.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() && checkList.isNotEmpty(),
            onClick = {
                val task = Task(
                    name = taskName,
                    description = taskDescription,
                    deadline = LocalDateTime.of(selectedDate, selectedTime),
                    progress = 0f
                )
                viewModel.createNewTask(task = task, checkList = checkList)
                navController.navigateUp()
            }
        ) {
            androidx.compose.material3.Text(
                text = "Create",
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CreateTaskHeader(
    onCancelClicked: () -> Unit,
    onResetClicked: () -> Unit,
    isResetEnabled: Boolean = false
) {
    val resetIconColor = animateColorAsState(
        targetValue = if (isResetEnabled) AppSecondaryColor else Color.Unspecified,
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { onCancelClicked() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.cancel_ic),
                contentDescription = null,
                tint = AppSecondaryColor
            )
        }
        Text(
            text = "Create Task",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { onResetClicked() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.reset_ic),
                contentDescription = null,
                tint = resetIconColor.value
            )
        }
    }
}

@Composable
fun DataEntrySection(
    taskName: String,
    onTaskNameChanged: (String) -> Unit,
    taskDescription: String,
    onTaskDescriptionChanged: (String) -> Unit,
    date: String,
    time: String,
    checkList: MutableList<String>,
    calenderState: UseCaseState,
    clockState: UseCaseState,
    scope: CoroutineScope
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CreateTaskScreenTextField(
            value = taskName,
            onValueChanged = { onTaskNameChanged(it) },
            placeHolder = "Tap to add task name",
            characterLimit = 50,
            height = 64.dp
        )
        Spacer(modifier = Modifier.height(40.dp))
        CreateTaskScreenTextField(
            value = taskDescription,
            onValueChanged = { onTaskDescriptionChanged(it) },
            placeHolder = "Tap to add task description",
            height = 120.dp,
            singleLine = false,
            characterLimit = 250
        )
        Spacer(modifier = Modifier.height(16.dp))
        CreateTaskScreenTextField(
            value = date,
            enabled = false,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scope.launch { calenderState.show() }
            },
            placeHolder = "date...",
            leadingIcon = R.drawable.calendar_ic
        )
        Spacer(modifier = Modifier.height(16.dp))
        CreateTaskScreenTextField(
            value = time,
            enabled = false,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scope.launch { clockState.show() }
            },
            placeHolder = "time...",
            leadingIcon = R.drawable.time_ic
        )
        Spacer(modifier = Modifier.height(16.dp))
        AddCheckListSection(checkList = checkList)
    }
}


@Composable
fun AddCheckListSection(
    checkList: MutableList<String>,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isExpanded by remember { mutableStateOf(false) }
    val arrowScale = animateFloatAsState(
        targetValue = if (isExpanded) -1f else 1f,
        label = ""
    )
    var newItemValue by remember {
        mutableStateOf("")
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(color = Color(0xFFEAE9F6), shape = RoundedCornerShape(16.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    isExpanded = !isExpanded
                }
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check_ic),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp, end = 8.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Check List",
                    color = Color(0xFF52465F),
                    fontFamily = SfDisplay,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                )
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (checkList.isEmpty()) "" else checkList.size.toString(),
                    color = Color(0xFF52465F),
                    fontFamily = SfDisplay,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_ic),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 12.dp)
                        .graphicsLayer {
                            scaleY = arrowScale.value
                            scaleX = arrowScale.value
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(
            visible = isExpanded,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(checkList.size) { index ->
                    CheckListItem(item = checkList[index]) {
                        checkList.removeAt(index)
                    }
                }
                item {
                    CreateTaskScreenTextField(
                        value = newItemValue,
                        onValueChanged = { newItemValue = it },
                        placeHolder = "Add item",
                        onDone = {
                            checkList.add(it)
                            newItemValue = ""
                        },
                        trailingIcon = R.drawable.check_icon,
                        onTrailingIconClicked = {
                            if(it.isNotEmpty()){
                                checkList.add(it)
                                newItemValue = ""
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CheckListItem(
    item: String,
    onDeleteClicked: () -> Unit
) {
    val itemAlpha = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(null) {
        itemAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 450,
                easing = LinearEasing
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement()
            .height(52.dp)
            .background(color = Color(0xFFEAE9F6), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp)
            .graphicsLayer {
                alpha = itemAlpha.value
            }
    ) {
        Text(
            text = item,
            color = Color(0xFF52465F),
            fontFamily = SfDisplay,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        IconButton(
            onClick = {
                onDeleteClicked()
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete_ic),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.offset(x = (16).dp)
            )
        }
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun CreateTaskScreenTextField(
    value: String,
    onValueChanged: (String) -> Unit = {},
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    onTrailingIconClicked: (String) -> Unit = {},
    placeHolder: String,
    height: Dp = 58.dp,
    characterLimit: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    onDone: ((String) -> Unit)? = null
) {
    val textMeasurer = rememberTextMeasurer()
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color = Color(0xFFEAE9F6), shape = RoundedCornerShape(16.dp))
            .padding(bottom = if (characterLimit == Int.MAX_VALUE) 0.dp else 10.dp)
            .drawWithContent {
                drawContent()
                if (characterLimit != Int.MAX_VALUE) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = "${value.length}/$characterLimit",
                        style = TextStyle(
                            color = if (value.length == characterLimit) Color(0xFFB00020) else Color(
                                0xFFC2B6CF
                            ),
                            fontFamily = SfDisplay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                        ),
                        topLeft = Offset(x = size.width - 160, y = size.height - 40)
                    )
                }
            },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = AppMainColor,
            disabledBorderColor = Color.Transparent
        ),
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            {
                IconButton(
                    onClick = { onTrailingIconClicked(value) }
                ) {
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

            }
        } else null,
        textStyle = TextStyle(
            color = Color(0xFF52465F),
            fontFamily = SfDisplay,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start
        ),
        placeholder = {
            Text(
                text = placeHolder,
                color = Color(0xFFC2B6CF),
                fontFamily = SfDisplay,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        },
        value = value,
        onValueChange = {
            if (it.length <= characterLimit) {
                onValueChanged(it)
            }
        },
        enabled = enabled,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = if (onDone != null) ImeAction.Done else ImeAction.Next),
        keyboardActions = KeyboardActions(
            onDone = {
                if (onDone != null) {
                    onDone(value)
                }
            }
        )
    )
}
