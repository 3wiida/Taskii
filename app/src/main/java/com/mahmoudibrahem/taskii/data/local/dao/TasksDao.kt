package com.mahmoudibrahem.taskii.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mahmoudibrahem.taskii.model.Task

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
    suspend fun updateTaskProgress(taskId: Int, progress: Float)

    @Query("SELECT * FROM Task WHERE id=:id")
    suspend fun getTaskById(id: Int): Task

    @Query("DELETE FROM Task WHERE id=:id")
    suspend fun deleteTask(id: Int)

    @Query("SELECT * FROM Task WHERE progress=1.0")
    suspend fun getCompletedTasks(): List<Task>

    @Query("SELECT * FROM Task WHERE progress<1.0")
    suspend fun getUnCompletedTasks(): List<Task>
}