package com.mahmoudibrahem.taskii.ui.screens.analytics

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    val completedTasksList = mutableStateListOf<Task>()
    val unCompletedTasksList = mutableStateListOf<Task>()
    fun getCompletedTasks(){
        viewModelScope.launch(Dispatchers.IO){
            completedTasksList.clear()
            completedTasksList.addAll(databaseRepository.getCompletedTasks())
        }
    }
    fun getUnCompletedTasks(){
        viewModelScope.launch(Dispatchers.IO){
            unCompletedTasksList.clear()
            unCompletedTasksList.addAll(databaseRepository.getUnCompletedTasks())
        }
    }
}