package com.mahmoudibrahem.taskii.repository.data_store

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnboardingState(state:Boolean)
    fun readOnboardingState(): Flow<Boolean?>

    suspend fun saveUserName(name:String)
    fun readUsername():Flow<String?>
}