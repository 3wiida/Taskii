package com.mahmoudibrahem.taskii.ui.screens.task_details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    private val _task = MutableStateFlow<Task?>(null)
    val task = _task.asStateFlow()

    var checkList:SnapshotStateList<CheckItem> = mutableStateListOf()

    fun getTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.value = databaseRepository.getTaskById(id)
        }
    }

    fun getCheckListByTaskId(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            checkList.clear()
            checkList.addAll(databaseRepository.getCheckItemsOfTask(taskId))
        }
    }

    fun saveTaskProcess(
        checkItem: CheckItem,
        checkItemIndex: Int,
        isCheckItemCompleted: Boolean
    ) {
        checkList[checkItemIndex] = checkItem.copy(isComplete = isCheckItemCompleted)
        _task.value = _task.value?.copy(progress = getNewProgress())
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.saveTaskProcess(
                task = _task.value!!,
                checkItem = checkList[checkItemIndex]
            )
        }
    }

    private fun getNewProgress(): Float {
        val totalItems = checkList.size
        val completedItems = checkList.filter { it.isComplete }.size
        return completedItems.div(totalItems.toFloat())
    }

    fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO){
            databaseRepository.deleteTask(_task.value!!.id)
        }
    }
}