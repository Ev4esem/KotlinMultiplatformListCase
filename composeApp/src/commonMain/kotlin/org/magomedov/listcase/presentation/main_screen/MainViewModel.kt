package org.magomedov.listcase.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.magomedov.listcase.core.IntentHandler
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.domain.repositories.MainRepository
import org.magomedov.listcase.domain.use_cases.ChangeStatusTaskUseCase
import org.magomedov.listcase.domain.use_cases.GetTasksUseCase
import org.magomedov.listcase.domain.use_cases.RemoveTaskUseCase

class MainViewModel: ViewModel(), IntentHandler<MainIntent> ,KoinComponent {

    private val repository by inject<MainRepository>()
    private val getTasksUseCase = GetTasksUseCase(repository)
    private val removeTaskUseCase = RemoveTaskUseCase(repository)
    private val changeStatusTaskUseCase = ChangeStatusTaskUseCase(repository)

    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch {
            getTasksUseCase().collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        tasks = it
                    )
                }
            }
        }
    }

    override fun handlerIntent(intent: MainIntent) {
        viewModelScope.launch {
            when(intent) {
                is MainIntent.ChangeTaskStatus -> {
                    changeStatusTaskUseCase(intent.taskId)
                }
                is MainIntent.DeleteTask -> {
                    removeTaskUseCase(intent.taskId)
                }
                is MainIntent.SelectTask -> {

                }
            }
        }
    }
}

data class MainState(
    val tasks: List<Task> = emptyList(),
    val countTask: Int = 0
)

sealed interface MainIntent {
    data class ChangeTaskStatus(val taskId: Long) : MainIntent
    data class SelectTask(val task: Task) : MainIntent
    data class DeleteTask(val taskId: Long) : MainIntent
}