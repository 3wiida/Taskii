package com.mahmoudibrahem.taskii.ui.screens.home

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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks = _allTasks.asStateFlow()

    private val _selectedTaskCheckItems = MutableStateFlow<List<CheckItem>>(emptyList())
    val selectedTaskCheckItems = _selectedTaskCheckItems.asStateFlow()

    init {
        getAllTasks()
        getCheckItemsOfTask(1)
    }

    val username = flow {
        val result = dataStoreRepository.readUsername()
            .flowOn(Dispatchers.IO)
            .first()
        emit(result)
    }

    fun upsertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.upsertTask(task)
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _allTasks.value = databaseRepository.getAllTasks()
        }
    }

    fun getCheckItemsOfTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedTaskCheckItems.value = databaseRepository.getCheckItemsOfTask(taskId)
        }
    }

    fun upsertCheckItem(checkItem:CheckItem){
        viewModelScope.launch(Dispatchers.IO){
            databaseRepository.upsertCheckItem(checkItem)
        }
    }

}