package org.magomedov.listcase.domain.use_cases

import kotlinx.coroutines.flow.Flow
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.domain.repositories.MainRepository

class GetTasksUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}