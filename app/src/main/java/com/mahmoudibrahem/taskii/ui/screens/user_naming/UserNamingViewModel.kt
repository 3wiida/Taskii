package com.mahmoudibrahem.taskii.ui.screens.user_naming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNamingViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository):ViewModel() {

    fun saveOnboardingState() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnboardingState(state = false)
        }
    }

    fun saveUsername(name:String){
        viewModelScope.launch(Dispatchers.IO){
            dataStoreRepository.saveUserName(name = name)
        }
    }
}