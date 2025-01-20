package org.magomedov.listcase.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.magomedov.listcase.domain.entities.Task

interface MainRepository {

    suspend fun addTask(task: Task)

    suspend fun removeTask(taskId: Long)

    suspend fun changeStatusTask(taskId: Long)

    fun getTaskById(taskId: Long): Flow<Task>

    fun getTasks(): Flow<List<Task>>

}