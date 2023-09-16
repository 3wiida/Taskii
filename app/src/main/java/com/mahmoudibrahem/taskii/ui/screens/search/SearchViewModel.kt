package com.mahmoudibrahem.taskii.ui.screens.search

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
class SearchViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    val searchResults = mutableStateListOf<Task>()

    fun searchForTasks(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchResults.clear()
            searchResults.addAll(databaseRepository.searchTask(searchQuery))
        }
    }
}