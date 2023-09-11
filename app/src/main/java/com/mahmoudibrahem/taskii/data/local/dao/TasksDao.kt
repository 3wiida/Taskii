package com.mahmoudibrahem.taskii.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.model.relations.TaskWithCheckItems

@Dao
interface TasksDao {
    @Upsert
    suspend fun upsertTask(task: Task)

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM Task WHERE name LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%'")
    suspend fun searchForTask(searchQuery: String): List<Task>

    @Query("SELECT MAX(id) FROM Task")
    suspend fun getLatestTaskId(): Int

    @Query("UPDATE Task SET progress=:progress WHERE id=:taskId")
    suspend fun updateTaskProgress(taskId: Int,progress:Float)

    @Transaction
    @Query("SELECT * FROM Task")
    suspend fun getTasksWithCheckList():List<TaskWithCheckItems>
}