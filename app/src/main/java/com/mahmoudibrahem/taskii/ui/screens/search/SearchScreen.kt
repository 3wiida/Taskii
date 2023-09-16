package com.mahmoudibrahem.taskii.ui.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mahmoudibrahem.taskii.R
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.navigation.screens.HomeScreens
import com.mahmoudibrahem.taskii.ui.theme.AppSecondaryColor
import com.mahmoudibrahem.taskii.ui.theme.SfDisplay

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    var isFirstOpen by remember {
        mutableStateOf(true)
    }
    var query by rememberSaveable {
            mutableStateOf("")
    }

    LaunchedEffect(key1 = query){
        if(query.isNotEmpty()) viewModel.searchForTasks(query) else viewModel.searchResults.clear()
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            SearchScreenHeader { navController.navigateUp() }
            Spacer(modifier = Modifier.height(16.dp))
            SearchWidget(
                searchQuery = query,
                onQueryChanged = { newQuery ->
                    query = newQuery
                },
                onClearClicked = {
                    query = ""
                },
                onSearchClicked = {
                    viewModel.searchForTasks(searchQuery = query)
                    isFirstOpen = false
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Result",
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            ResultsSection(
                resultsList = viewModel.searchResults,
                onResultClicked = {
                    navController.navigate(
                        route = HomeScreens.TaskDetails.route.replace(
                            "{task_id}",
                            it.id.toString()
                        )
                    )
                }
            )
            SearchEmptyState(
                resultsList = viewModel.searchResults,
                isFirstOpen = isFirstOpen
            )
        }
    }
}

@Composable
fun SearchScreenHeader(
    onBackClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-16).dp),
            onClick = { onBackClicked() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_ic),
                contentDescription = null,
                tint = AppSecondaryColor
            )
        }
        Text(
            text = "Search",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SearchWidget(
    searchQuery: String = "",
    onQueryChanged: (String) -> Unit = {},
    onClearClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {}
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onQueryChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = Color(0xFFEAE9F6), shape = RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_ic),
                contentDescription = null,
                tint = Color(0xFFC2B6CF)
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = { onClearClicked() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_ic),
                        contentDescription = null,
                        tint = AppSecondaryColor
                    )
                }
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = SfDisplay,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start
        ),
        placeholder = {
            Text(
                text = "Search...",
                color = Color(0xFFC2B6CF),
                fontFamily = SfDisplay,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchClicked() })
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultsSection(
    resultsList: List<Task> = emptyList(),
    onResultClicked: (Task) -> Unit
) {
    AnimatedVisibility(visible = resultsList.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(resultsList.size) { index ->
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color(0xFF52465F),
                                fontFamily = SfDisplay,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        ) {
                            append(resultsList[index].name)
                        }
                    },
                    onClick = {
                        onResultClicked(resultsList[index])
                    },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@Composable
fun SearchEmptyState(
    resultsList: List<Task> = emptyList(),
    isFirstOpen: Boolean
) {
    AnimatedVisibility(
        visible = (resultsList.isEmpty() && !isFirstOpen),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_search),
                contentDescription = null,
                modifier = Modifier.size(240.dp)
            )
            Text(
                text = "No have result please try again !",
                color = Color(0xFFC2B6CF),
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}