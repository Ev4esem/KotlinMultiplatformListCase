package org.magomedov.listcase.data.local.db

import kotlinx.coroutines.withContext
import org.magomedov.listcase.data.mapper.toDataLayer
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.listCaseDispatchers

class TaskDao(
    private val listCaseDatabase: ListCaseDatabase
) {
    private val query get() = listCaseDatabase.taskEntityQueries

    suspend fun getTasks() = withContext(listCaseDispatchers.io) {
        query.getAllTask().executeAsList()
    }

    suspend fun addTask(task: Task) = withContext(listCaseDispatchers.io) {
        query.addTask(task.toDataLayer())
    }

    suspend fun removeTask(taskId: Long) = withContext(listCaseDispatchers.io) {
        query.deleteTask(taskId)
    }

    suspend fun getTaskById(taskId: Long) = withContext(listCaseDispatchers.io) {
        query.getTaskById(taskId).executeAsOne()
    }

    suspend fun changeStatusTask(taskId: Long) = withContext(listCaseDispatchers.io) {
        query.updateTaskStatus(taskId)
    }

}