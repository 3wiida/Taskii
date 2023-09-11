package com.mahmoudibrahem.taskii.data.local.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudibrahem.taskii.data.local.dao.CheckItemDao
import com.mahmoudibrahem.taskii.data.local.dao.TasksDao
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.util.Converters

@Database(entities = [Task::class,CheckItem::class], version = 4, exportSchema = true)
@TypeConverters(Converters::class)
abstract class TaskiiDatabase : RoomDatabase() {
    abstract fun getTasksDao(): TasksDao
    abstract fun getCheckItemDao():CheckItemDao
}