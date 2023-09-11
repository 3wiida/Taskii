package com.mahmoudibrahem.taskii.ui.screens.create_task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {
    fun createNewTask(task: Task, checkList: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.createNewTask(task = task, checkList = checkList)
        }
    }

}