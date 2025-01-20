package org.magomedov.listcase.domain.use_cases

import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.domain.repositories.MainRepository

class AddTaskUseCase (
    private val repository: MainRepository
) {
    suspend operator fun invoke(task: Task) {
        repository.addTask(task)
    }
}