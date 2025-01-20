package org.magomedov.listcase.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.magomedov.listcase.data.local.db.TaskDao
import org.magomedov.listcase.data.mapper.toDomainLayer
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.domain.repositories.MainRepository

class MainRepositoryImpl : MainRepository, KoinComponent {

    private val taskDao by inject<TaskDao>()

    private val taskListChangeEvents = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    override suspend fun addTask(task: Task) {
         taskDao.addTask(task)
        taskListChangeEvents.tryEmit(Unit)
    }

    override suspend fun removeTask(taskId: Long) {
        taskDao.removeTask(taskId)
        taskListChangeEvents.tryEmit(Unit)
    }

    override suspend fun changeStatusTask(taskId: Long) {
        taskDao.changeStatusTask(taskId)
        taskListChangeEvents.tryEmit(Unit)
    }

    override fun getTaskById(taskId: Long): Flow<Task> = flow {
       val task = taskDao.getTaskById(taskId).toDomainLayer()
       emit(task)
    }

    override fun getTasks(): Flow<List<Task>> = flow {
        taskListChangeEvents.collect {
            val tasks = taskDao.getTasks()
            emit(tasks.toDomainLayer())
        }
    }
}