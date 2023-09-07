package com.mahmoudibrahem.taskii.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepository
import com.mahmoudibrahem.taskii.repository.data_store.DataStoreRepositoryImpl
import com.mahmoudibrahem.taskii.util.Constants.DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.appDataStore by preferencesDataStore(name = DATASTORE_FILE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.appDataStore
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository {
        return dataStoreRepositoryImpl
    }
}