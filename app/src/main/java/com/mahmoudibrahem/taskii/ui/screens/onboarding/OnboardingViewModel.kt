package com.mahmoudibrahem.taskii.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel()