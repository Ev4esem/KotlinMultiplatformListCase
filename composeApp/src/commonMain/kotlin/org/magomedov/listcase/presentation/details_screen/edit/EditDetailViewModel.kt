package org.magomedov.listcase.presentation.details_screen.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.magomedov.listcase.core.IntentHandler
import org.magomedov.listcase.core.toCalendar
import org.magomedov.listcase.domain.entities.Importance
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.domain.repositories.MainRepository
import org.magomedov.listcase.domain.use_cases.AddTaskUseCase
import org.magomedov.listcase.domain.use_cases.GetTaskByIdUseCase
import org.magomedov.listcase.domain.use_cases.RemoveTaskUseCase
import org.magomedov.listcase.presentation.details_screen.ImportanceItem

class EditDetailViewModel: ViewModel(), KoinComponent, IntentHandler<EditIntent> {

    private val repository by inject<MainRepository>()
    private val addTaskUseCase = AddTaskUseCase(repository)
    private val getTaskByIdUseCase = GetTaskByIdUseCase(repository)
    private val removeTaskUseCase = RemoveTaskUseCase(repository)

    private val _uiState = MutableStateFlow(EditState())
    val uiState: StateFlow<EditState> = _uiState.asStateFlow()

    override fun handlerIntent(intent: EditIntent) {
        viewModelScope.launch {
            when(intent) {
                is EditIntent.ChangeDescription -> {
                    changeDescription(intent.description)
                }
                is EditIntent.SaveTask -> {
                    addTaskUseCase(uiState.value.task)
                }

                is EditIntent.ChangeSwitch -> {
                    changeSwitch(intent.isSwitch)
                }

                is EditIntent.ChooseDate -> {
                    chooseDate(intent.date)
                }

                is EditIntent.DismissDialog -> {
                    dismissDialog()
                }

                is EditIntent.ChooseDropdownItem -> {
                    chooseDropdownMenu(intent.importance)
                }
                is EditIntent.DismissDropdownMenu -> {
                    dismissDropdownMenu()
                }
                is EditIntent.OpenDropdownMenu -> {
                    openDropdownMenu()
                }

                is EditIntent.Init -> {
                    init(intent.taskId)
                }

                is EditIntent.DeleteTask -> {
                    removeTaskUseCase(intent.taskId)
                }
            }
        }
    }

    private suspend fun init(taskId: Long) {
        getTaskByIdUseCase(taskId).collect {
            _uiState.update { currentState ->
                currentState.copy(
                    task = it,
                    isSwitch = it.date != null
                )
            }
        }
    }

    private fun chooseDropdownMenu(importance: Importance) {
        _uiState.update { currentState ->
            currentState.copy(
                task = currentState.task.copy(
                    importance = importance,
                )
            )
        }
        dismissDropdownMenu()
    }

    private fun dismissDropdownMenu() {
        _uiState.update { currentState ->
            currentState.copy(
                isExpandedDropdownMenu = false
            )
        }
    }

    private fun openDropdownMenu() {
        _uiState.update { currentState ->
            currentState.copy(
                isExpandedDropdownMenu = true
            )
        }
    }

    private fun changeDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                task = currentState.task.copy(
                    title = description
                )
            )
        }
    }

    private fun dismissDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowDialog = false,
                isSwitch = false
            )
        }
    }

    private fun chooseDate(date: Long) {
        _uiState.update { currentState ->
            currentState.copy(
                task = currentState.task.copy(
                    date = date.toCalendar(),
                ),
                isShowDialog = false
            )
        }
    }

    private fun changeSwitch(isSwitch: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                task = currentState.task.copy(
                    date = if (isSwitch) currentState.task.date else null
                ),
                isSwitch = isSwitch,
                isShowDialog = isSwitch,
            )
        }
    }

    fun clear() {
        _uiState.value = EditState(
            task = Task(
                title = "",
                date = ""
            )
        )
    }
}

data class EditState(
    val task: Task = Task(
        title = "",
        date = ""
    ),
    val dropdownList: List<ImportanceItem> = ImportanceItem.importanceList,
    val isShowDialog: Boolean = false,
    val isSwitch: Boolean = false,
    val isExpandedDropdownMenu: Boolean = false,
)

sealed interface EditIntent {
    data class Init(val taskId: Long): EditIntent
    data class DeleteTask(val taskId: Long): EditIntent
    data class ChangeDescription(val description: String): EditIntent
    data class ChangeSwitch(val isSwitch: Boolean): EditIntent
    data class ChooseDate(val date: Long): EditIntent
    data object DismissDialog: EditIntent
    data object SaveTask: EditIntent
    data object DismissDropdownMenu: EditIntent
    data object OpenDropdownMenu: EditIntent
    data class ChooseDropdownItem(val importance: Importance): EditIntent
}