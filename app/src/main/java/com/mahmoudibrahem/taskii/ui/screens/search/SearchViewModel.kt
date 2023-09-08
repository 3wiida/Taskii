package com.mahmoudibrahem.taskii.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    private val _searchResults = MutableStateFlow<List<Task>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    fun searchForTasks(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchResults.value = databaseRepository.searchTask(searchQuery)
        }
    }
}