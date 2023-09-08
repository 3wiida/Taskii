package com.mahmoudibrahem.taskii.repository.database

import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.model.relations.TaskWithCheckItems

interface DatabaseRepository {
    suspend fun upsertTask(task: Task)

    suspend fun getAllTasks(): List<Task>

    suspend fun upsertCheckItem(checkItem: CheckItem)
    suspend fun getCheckItemsOfTask(taskId: Int): List<CheckItem>

    suspend fun searchTask(searchQuery: String): List<Task>
}