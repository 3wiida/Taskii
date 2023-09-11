package com.mahmoudibrahem.taskii.di

import android.content.Context
import androidx.room.Room
import com.mahmoudibrahem.taskii.data.local.database.TaskiiDatabase
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepository
import com.mahmoudibrahem.taskii.repository.database.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TaskiiDatabase::class.java,
            "TaskiiDatabase"
        )
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTasksDao(db: TaskiiDatabase) = db.getTasksDao()

    @Provides
    @Singleton
    fun provideCheckItemDao(db: TaskiiDatabase) = db.getCheckItemDao()

    @Provides
    @Singleton
    fun provideDatabaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository {
        return databaseRepositoryImpl
    }
}