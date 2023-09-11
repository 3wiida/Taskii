package com.mahmoudibrahem.taskii.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.model.relations.TaskWithCheckItems
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepository
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val tasks = mutableStateListOf<Task>()
    val selectedTaskCheckList = mutableStateListOf<CheckItem>()

    val username = flow {
        val result = dataStoreRepository.readUsername()
            .flowOn(Dispatchers.IO)
            .first()
        emit(result)
    }

    fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            tasks.clear()
            tasks.addAll(databaseRepository.getAllTasks())
        }
    }

    fun getCheckListByTaskId(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedTaskCheckList.clear()
            selectedTaskCheckList.addAll(databaseRepository.getCheckItemsOfTask(taskId))
        }
    }

    fun saveTaskProcess(
        checkItem: CheckItem,
        taskIndex: Int,
        checkItemIndex: Int,
        isCheckItemCompleted: Boolean
    ) {
        selectedTaskCheckList[checkItemIndex] = checkItem.copy(isComplete = isCheckItemCompleted)
        tasks[taskIndex] = tasks[taskIndex].copy(progress = getNewProgress())
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.saveTaskProcess(
                task = tasks[taskIndex],
                checkItem = selectedTaskCheckList[checkItemIndex]
            )
        }
    }

    private fun getNewProgress(): Float {
        val totalItems = selectedTaskCheckList.size
        val completedItems = selectedTaskCheckList.filter { it.isComplete }.size
        return completedItems.div(totalItems.toFloat())
    }

    fun getFinalDateFormat(dateTime: LocalDateTime): String {
        return "${dateTime.month.name} ${dateTime.dayOfMonth} at ${dateTime.hour}:${dateTime.minute}"
    }
}