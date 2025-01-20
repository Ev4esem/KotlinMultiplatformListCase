package org.magomedov.listcase.presentation.details_screen.add

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
import org.magomedov.listcase.presentation.details_screen.ImportanceItem

class AddViewModel: KoinComponent, IntentHandler<AddIntent>, ViewModel() {

    private val repository by inject<MainRepository>()
    private val addTaskUseCase = AddTaskUseCase(repository)

    private val _uiState = MutableStateFlow(AddState())
    val uiState: StateFlow<AddState> = _uiState.asStateFlow()

    override fun handlerIntent(intent: AddIntent) {
        viewModelScope.launch {
            when(intent) {
                is AddIntent.ChangeDescription -> {
                    changeDescription(intent.description)
                }
                is AddIntent.SaveTask -> {
                    addTaskUseCase(uiState.value.task)
                }

                is AddIntent.ChangeSwitch -> {
                    changeSwitch(intent.isSwitch)
                }

                is AddIntent.ChooseDate -> {
                    chooseDate(intent.date)
                }

                is AddIntent.DismissDialog -> {
                    dismissDialog()
                }

                is AddIntent.ChooseDropdownItem -> {
                    chooseDropdownMenu(intent.importance)
                }
                is AddIntent.DismissDropdownMenu -> {
                    dismissDropdownMenu()
                }
                is AddIntent.OpenDropdownMenu -> {
                    openDropdownMenu()
                }
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
                isSwitch = isSwitch,
                isShowDialog = isSwitch,
            )
        }
    }

    fun clear() {
        _uiState.value = AddState()
    }

}

data class AddState(
    val task: Task = Task(
        title = "",
        date = null,
    ),
    val dropdownList: List<ImportanceItem> = ImportanceItem.importanceList,
    val isShowDialog: Boolean = false,
    val isSwitch: Boolean = false,
    val isExpandedDropdownMenu: Boolean = false,
)

sealed interface AddIntent {
    data class ChangeDescription(val description: String): AddIntent
    data class ChangeSwitch(val isSwitch: Boolean): AddIntent
    data class ChooseDate(val date: Long): AddIntent
    data object DismissDialog: AddIntent
    data object SaveTask: AddIntent
    data object DismissDropdownMenu: AddIntent
    data object OpenDropdownMenu: AddIntent
    data class ChooseDropdownItem(val importance: Importance): AddIntent
}