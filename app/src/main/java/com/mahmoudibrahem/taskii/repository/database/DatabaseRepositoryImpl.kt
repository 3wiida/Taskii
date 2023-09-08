package com.mahmoudibrahem.taskii.repository.database

import com.mahmoudibrahem.taskii.data.local.dao.TasksDao
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.model.relations.TaskWithCheckItems
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val tasksDao: TasksDao) :
    DatabaseRepository {
    override suspend fun upsertTask(task: Task) {
        tasksDao.upsertTask(task)
    }

    override suspend fun getAllTasks(): List<Task> {
        return tasksDao.getAllTasks()
    }

    override suspend fun upsertCheckItem(checkItem: CheckItem) {
        tasksDao.upsertCheckItem(checkItem)
    }

    override suspend fun getCheckItemsOfTask(taskId: Int): List<CheckItem> {
        return tasksDao.getCheckItemsOfTask(taskId)
    }

    override suspend fun searchTask(searchQuery: String): List<Task> {
        return tasksDao.searchForTask(searchQuery)
    }

}