package com.mahmoudibrahem.taskii.repository.database

import androidx.room.withTransaction
import com.mahmoudibrahem.taskii.data.local.dao.CheckItemDao
import com.mahmoudibrahem.taskii.data.local.dao.TasksDao
import com.mahmoudibrahem.taskii.data.local.database.TaskiiDatabase
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task
import com.mahmoudibrahem.taskii.model.relations.TaskWithCheckItems
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val checkItemDao: CheckItemDao,
    private val db: TaskiiDatabase
) :
    DatabaseRepository {
    override suspend fun upsertTask(task: Task) {
        tasksDao.upsertTask(task)
    }

    override suspend fun getAllTasks(): List<Task> {
        return tasksDao.getAllTasks()
    }

    override suspend fun upsertCheckItem(checkItem: CheckItem) {
        checkItemDao.upsertCheckItem(checkItem)
    }

    override suspend fun getCheckItemsOfTask(taskId: Int): List<CheckItem> {
        return checkItemDao.getCheckItemsOfTask(taskId)
    }

    override suspend fun searchTask(searchQuery: String): List<Task> {
        return tasksDao.searchForTask(searchQuery)
    }

    override suspend fun getLatestTaskId(): Int {
        return tasksDao.getLatestTaskId()
    }

    override suspend fun updateTaskProgress(taskId: Int, progress: Float) {
        tasksDao.updateTaskProgress(taskId, progress)
    }

    override suspend fun createNewTask(task: Task, checkList: List<String>) {
        db.withTransaction {
            upsertTask(task)
            val latestTaskId = getLatestTaskId()
            checkList.forEach { content ->
                val checkItem = CheckItem(taskId = latestTaskId, content = content)
                upsertCheckItem(checkItem)
            }
        }
    }

    override suspend fun getTasksWithCheckList(): List<TaskWithCheckItems> {
        return tasksDao.getTasksWithCheckList()
    }

    override suspend fun saveTaskProcess(task: Task, checkItem: CheckItem) {
        db.withTransaction {
            upsertCheckItem(checkItem)
            upsertTask(task)
        }
    }
}