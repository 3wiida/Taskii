package com.mahmoudibrahem.taskii.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.navigation.screens.HomeScreens
import com.mahmoudibrahem.taskii.ui.theme.AppGreenColor
import com.mahmoudibrahem.taskii.ui.theme.AppMainColor
import com.mahmoudibrahem.taskii.ui.theme.AppSecondaryColor
import com.mahmoudibrahem.taskii.ui.theme.LightTextColor
import com.mahmoudibrahem.taskii.ui.theme.SfDisplay
import com.mahmoudibrahem.taskii.util.shadow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    var selectedTaskIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                selectedScreen = 0,
                onAddClicked = {
                    navController.navigate(route = HomeScreens.CreateTask.route)
                },
                onAnalyticsClicked = {
                    navController.navigate(route = HomeScreens.Analytics.route)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 16.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 95.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeTopBar(
                onSearchButtonClicked = {
                    navController.navigate(
                        route = HomeScreens.Search.route
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            GreetingSection(
                username = viewModel.username.collectAsState(initial = "").value ?: "",
                tasksCount = viewModel.tasks.size
            )
            Spacer(modifier = Modifier.height(24.dp))
            TasksSection(
                tasks = viewModel.tasks,
                selectedItemIndex = selectedTaskIndex,
                onTaskClicked = { index, id ->
                    viewModel.getCheckListByTaskId(id)
                    selectedTaskIndex = index
                },
                onGoClicked = { task ->
                    navController.navigate(
                        route = HomeScreens.TaskDetails.route.replace(
                            "{task_id}",
                            task.id.toString()
                        )
                    )
                },
                onTaskCompleted = {
                    viewModel.getUncompletedTasks()
                    selectedTaskIndex=0
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedVisibility(
                visible = viewModel.tasks.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CheckListSection(
                    headerText = "Task Checklist",
                    checkList = viewModel.selectedTaskCheckList,
                    onCompleteCheckItem = { item, isCompleted, index ->
                        viewModel.saveTaskProcess(
                            checkItem = item,
                            taskIndex = selectedTaskIndex,
                            checkItemIndex = index,
                            isCheckItemCompleted = isCompleted
                        )
                    }
                )
            }
            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        viewModel.getUncompletedTasks()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }
        }
    }

}


@Composable
fun HomeTopBar(
    onSearchButtonClicked: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = AppMainColor,
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                ) {
                    append("T")
                }
                withStyle(
                    SpanStyle(
                        color = Color.Black,
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                ) {
                    append("askii")
                }
            },
            modifier = Modifier.align(Alignment.CenterStart)
        )

        IconButton(
            onClick = { onSearchButtonClicked() },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_ic),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun GreetingSection(
    username: String,
    tasksCount: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello, $username!",
            fontFamily = SfDisplay,
            fontSize = 16.sp,
            color = LightTextColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                ) {
                    append("You've got ")
                }
                withStyle(
                    SpanStyle(
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = AppMainColor
                    )
                ) {
                    append(tasksCount.toString())
                }
                withStyle(
                    SpanStyle(
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                ) {
                    append(" uncompleted\n tasks")
                }
            }
        )
    }
}

@Composable
fun TasksSection(
    tasks: List<Task>,
    selectedItemIndex: Int = 0,
    onTaskClicked: (index: Int, id: Int) -> Unit,
    onGoClicked: (task: Task) -> Unit,
    onTaskCompleted: (task: Task) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
    ) {
        items(tasks.size) { index ->
            TaskCard(
                task = tasks[index],
                onTaskClicked = { id -> onTaskClicked(index, id) },
                isSelected = index == selectedItemIndex,
                onGoClicked = { task -> onGoClicked(task) },
                onTaskCompleted = {task ->  onTaskCompleted(task)}
            )
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TaskCard(
    task: Task,
    isSelected: Boolean = true,
    onTaskClicked: (id: Int) -> Unit,
    onGoClicked: (task: Task) -> Unit,
    onTaskCompleted:(task:Task) -> Unit
) {
    val borderColor = animateColorAsState(
        targetValue = if (isSelected) AppSecondaryColor else Color.Transparent,
        label = "",
        animationSpec = tween(100)
    )
    val progressValue = animateFloatAsState(
        targetValue = task.progress,
        label = "",
        animationSpec = tween(800)
    )
    if (progressValue.value == 1.0f) {
        onTaskCompleted(task)
    }
    Surface(
        modifier = Modifier
            .animateItemPlacement()
            .size(width = 185.dp, height = 195.dp)
            .shadow(
                color = Color(0xA000000),
                blurRadius = 48.dp,
                offsetY = 8.dp,
                offsetX = 4.dp
            )
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onTaskClicked(task.id) },
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = task.name,
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(end = 30.dp)
                )
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 12.sp,
                                fontFamily = SfDisplay,
                                color = AppGreenColor,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("show")
                        }

                    },
                    onClick = { onGoClicked(task) },
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }

            Text(
                text = task.description,
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                color = LightTextColor,
                maxLines = 1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Process",
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Text(
                    text = "${(progressValue.value * 100).toInt()}%",
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = AppGreenColor
                )
            }
            LinearProgressIndicator(
                progress = progressValue.value,
                strokeCap = StrokeCap.Round,
                color = AppGreenColor
            )
            Text(
                text = "Deadline: ${
                    task.deadline.month.name.lowercase().slice(0..2)
                } ${task.deadline.dayOfMonth} at ${task.deadline.hour}:${task.deadline.minute}",
                color = AppMainColor,
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFE8C8),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(4.dp),
                maxLines = 1,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun CheckListSection(
    headerText: String,
    checkList: List<CheckItem>,
    onCompleteCheckItem: (CheckItem, Boolean, Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = headerText,
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(checkList.size) { index ->
                CheckListItem(
                    item = checkList[index],
                    onCompleteCheckItem = { item, isCompleted ->
                        onCompleteCheckItem(
                            item,
                            isCompleted,
                            index
                        )
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CheckListItem(
    item: CheckItem,
    onCompleteCheckItem: (CheckItem, Boolean) -> Unit
) {
    var isComplete by remember(item.taskId) { mutableStateOf(item.isComplete) }
    val lineScale = animateFloatAsState(targetValue = if (isComplete) 1f else 0f, label = "")
    val itemAlpha = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(null) {
        itemAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
            )
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = itemAlpha.value
            }
            .background(
                color = Color(0xFFF7F6FB),
                shape = RoundedCornerShape(16.dp)
            )
            .offset(x = (-16).dp)
            .animateItemPlacement()
            .padding(horizontal = 12.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = isComplete,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Gray,
                unselectedColor = AppGreenColor
            ),
            onClick = {
                isComplete = !isComplete
                onCompleteCheckItem(item, isComplete)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.content,
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.drawBehind {
                if (lineScale.value > 0f) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width * lineScale.value, size.height / 2),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                }
            }
        )
    }
}

@Composable
fun AppBottomBar(
    selectedScreen: Int = 0,
    onHomeClicked: () -> Unit = {},
    onAnalyticsClicked: () -> Unit = {},
    onAddClicked: () -> Unit = {},
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        val width = this.minWidth
        val height = this.minHeight
        Card(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.shadow(
                blurRadius = 16.dp,
                color = Color(0x14000000),
                offsetY = (-4).dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            color = Color.Green,
                            radius = 48.dp.toPx(),
                            center = Offset(x = size.width / 2, y = 0f),
                            blendMode = BlendMode.Clear
                        )
                    }
                    .shadow(color = Color.Black, offsetY = (-20).dp, spread = 20.dp)
                    .background(Color.White)
                    .padding(horizontal = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        onHomeClicked()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_ic),
                        contentDescription = null,
                        tint = if (selectedScreen == 0) Color.Unspecified else Color(0xFFE0C8FE),
                        modifier = Modifier.size(22.dp)
                    )
                }
                IconButton(
                    onClick = {
                        onAnalyticsClicked()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.analytics_ic),
                        contentDescription = null,
                        tint = if (selectedScreen == 1) Color.Unspecified else Color(0xFFE0C8FE),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .offset(width.div(2) - 36.dp, -height.div(2) + 20.dp)
                .shadow(
                    color = Color(0x7AFF9300),
                    blurRadius = 16.dp,
                    spread = 1.dp,
                    borderRadius = 100.dp,
                    offsetY = 8.dp
                )
                .size(72.dp)
                .clip(CircleShape)
                .background(AppMainColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onAddClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(id = R.drawable.add_ic),
                contentDescription = "",
                tint = Color.Unspecified
            )
        }
    }
}
