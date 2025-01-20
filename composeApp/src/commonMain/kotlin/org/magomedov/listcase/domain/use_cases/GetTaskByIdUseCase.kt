package org.magomedov.listcase.domain.use_cases

import kotlinx.coroutines.flow.Flow
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.domain.repositories.MainRepository

class GetTaskByIdUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(taskId: Long): Flow<Task> {
        return repository.getTaskById(taskId)
    }
}