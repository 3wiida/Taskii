package com.mahmoudibrahem.taskii.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
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