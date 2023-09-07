package com.mahmoudibrahem.taskii.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val ONBOARDING_STATE_KEY = booleanPreferencesKey("onboarding_state_key")
    val USERNAME_KEY= stringPreferencesKey("username_key")
}