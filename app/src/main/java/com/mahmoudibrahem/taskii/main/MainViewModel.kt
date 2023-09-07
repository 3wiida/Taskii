package com.mahmoudibrahem.taskii.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {

    val isKeepSplash = MutableStateFlow(true)

    val isShowOnboarding = flow {
        val result = dataStoreRepository.readOnboardingState()
            .flowOn(Dispatchers.IO)
            .first()
        emit(result)
    }

    init {
        viewModelScope.launch {
            delay(1500)
            isKeepSplash.value = false
        }
    }

}