package com.mahmoudibrahem.taskii.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.model.CheckListItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.ui.theme.AppGreenColor
import com.mahmoudibrahem.taskii.ui.theme.AppMainColor
import com.mahmoudibrahem.taskii.ui.theme.AppSecondaryColor
import com.mahmoudibrahem.taskii.ui.theme.LightTextColor
import com.mahmoudibrahem.taskii.ui.theme.SfDisplay
import com.mahmoudibrahem.taskii.util.shadow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = { AppBottomBar() }
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
                onMenuButtonClicked = {},
                onSearchButtonClicked = {},
                onCalendarButtonClicked = {}
            )
            Spacer(modifier = Modifier.height(24.dp))
            GreetingSection(
                username = "Mahmoud",
                tasksCount = 18
            )
            Spacer(modifier = Modifier.height(24.dp))
            TasksSection(
                tasks = listOf(
                    Task(
                        1,
                        "Gemii App",
                        "Creating userflow",
                        "20, Oc, 14:00",
                        0.33f
                    ),
                    Task(
                        2,
                        "Taskii App",
                        "Creating UX mapping",
                        " 20, Oc, 18:00",
                        0.99f
                    )
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            CheckListSection(
                checkList = listOf(
                    CheckListItem(
                        1,
                        "Creating UI design systerm",
                        false
                    ),
                    CheckListItem(
                        2,
                        "Flow task for update",
                        false
                    ),
                    CheckListItem(
                        3,
                        "Check Userflow and Architure Infomation bla bla bla",
                        true
                    ),
                    CheckListItem(
                        4,
                        "This is my spacing for me of coures",
                        false
                    )
                )
            )
        }
    }

}



@Composable
fun HomeTopBar(
    onMenuButtonClicked: () -> Unit = {},
    onSearchButtonClicked: () -> Unit = {},
    onCalendarButtonClicked: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { onMenuButtonClicked() },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-16).dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu_ic),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (16).dp),
        ) {
            IconButton(
                onClick = { onSearchButtonClicked() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_ic),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            IconButton(
                onClick = { onCalendarButtonClicked() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_ic),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
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
            text = "Hello,$username!",
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
                        fontSize = 28.sp,
                        color = Color.Black
                    )
                ) {
                    append("You've got ")
                }
                withStyle(
                    SpanStyle(
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = AppMainColor
                    )
                ) {
                    append(tasksCount.toString())
                }
                withStyle(
                    SpanStyle(
                        fontFamily = SfDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.Black
                    )
                ) {
                    append(" task\n today")
                }
            }
        )
    }
}

@Composable
fun TasksSection(tasks: List<Task>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(tasks.size) { index ->
            TaskCard(
                taskName = tasks[index].name,
                taskDescription = tasks[index].description,
                progress = tasks[index].progress,
                deadLine = tasks[index].deadline
            )
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TaskCard(
    taskName: String,
    taskDescription: String,
    progress: Float,
    deadLine: String,
    isSelected: Boolean = true
) {
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
                color = if (isSelected) AppSecondaryColor else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = taskName,
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                maxLines = 1
            )
            Text(
                text = taskDescription,
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
                    text = "${(progress * 100).toInt()}%",
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = AppGreenColor
                )
            }
            LinearProgressIndicator(
                progress = progress,
                strokeCap = StrokeCap.Round,
                color = AppGreenColor
            )
            Text(
                text = "Deadline: $deadLine",
                color = AppMainColor,
                modifier = Modifier.background(
                    color = Color(0xFFFFE8C8),
                    shape = RoundedCornerShape(4.dp)
                ).padding(4.dp),
                maxLines = 1
            )
        }
    }
}

@Composable
fun CheckListSection(checkList: List<CheckListItem>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Your checklist",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(checkList.size) { index ->
                CheckListItem(item = checkList[index])
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CheckListItem(item: CheckListItem) {
    val lineScale = animateFloatAsState(
        targetValue = if (item.isComplete) 1f else 0f,
        label = "",
        animationSpec = tween(durationMillis = 1000)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x2652465F),
                shape = RoundedCornerShape(16.dp)
            )
            .offset(x = (-16).dp)
            .animateItemPlacement()
            .padding(horizontal = 12.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = item.isComplete,
            onClick = {

            },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Gray,
                unselectedColor = AppGreenColor
            )
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
                this.drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width * lineScale.value, size.height / 2),
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )
            }
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun AppBottomBar() {

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

                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_ic),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(22.dp)
                    )
                }
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.analytics_ic),
                        contentDescription = null,
                        tint = Color.Unspecified,
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
                .background(AppMainColor),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    painterResource(id = R.drawable.add_ic),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
    }


}








