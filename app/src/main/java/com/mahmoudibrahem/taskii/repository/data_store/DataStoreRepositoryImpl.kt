package com.mahmoudibrahem.taskii.repository.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mahmoudibrahem.taskii.util.DataStoreKeys.ONBOARDING_STATE_KEY
import com.mahmoudibrahem.taskii.util.DataStoreKeys.USERNAME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreRepository {

    override fun readOnboardingState(): Flow<Boolean?> {
        return dataStore.data
            .catch { cause: Throwable ->
                cause.printStackTrace()
            }.map { value: Preferences ->
                value[ONBOARDING_STATE_KEY]
            }
    }

    override suspend fun saveOnboardingState(state: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[ONBOARDING_STATE_KEY] = state
        }
    }

    override suspend fun saveUserName(name: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[USERNAME_KEY] = name
        }
    }

    override fun readUsername(): Flow<String?> {
        return dataStore.data
            .catch {cause:Throwable ->
                cause.printStackTrace()
            }.map {value: Preferences ->
                value[USERNAME_KEY]
            }
    }

}