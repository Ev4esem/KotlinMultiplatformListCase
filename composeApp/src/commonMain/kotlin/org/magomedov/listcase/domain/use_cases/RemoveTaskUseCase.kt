package org.magomedov.listcase.domain.use_cases

import org.magomedov.listcase.domain.repositories.MainRepository

class RemoveTaskUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(taskId: Long) {
        repository.removeTask(taskId)
    }
}