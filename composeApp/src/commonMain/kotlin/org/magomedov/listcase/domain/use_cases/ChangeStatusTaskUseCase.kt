package org.magomedov.listcase.domain.use_cases

import org.magomedov.listcase.domain.repositories.MainRepository

class ChangeStatusTaskUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(taskId: Long) {
        repository.changeStatusTask(taskId)
    }
}