package com.mahmoudibrahem.taskii.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.model.relations.TaskWithCheckItems

@Dao
interface TasksDao {
    @Upsert
    suspend fun upsertTask(task:Task)

    @Query("SELECT * FROM task")
    suspend fun getAllTasks():List<Task>

    @Query("SELECT * FROM CheckItem WHERE taskId=:taskId")
    suspend fun getCheckItemsOfTask(taskId:Int):List<CheckItem>

    @Upsert
    suspend fun upsertCheckItem(checkItem: CheckItem)

    @Query("SELECT * FROM Task WHERE name LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%'")
    suspend fun searchForTask(searchQuery:String):List<Task>
}