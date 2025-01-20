package org.magomedov.listcase.presentation.details_screen.composable

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import kotlinmultiplatformlistcase.composeapp.generated.resources.Res
import kotlinmultiplatformlistcase.composeapp.generated.resources.details_screen_button_save
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    onSaveRequest: (Long) -> Unit,
    onBackRequest: () -> Unit,
) {
    val state = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onBackRequest,
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small),
                onClick = {
                    if (state.selectedDateMillis != null) {
                        onSaveRequest(state.selectedDateMillis!!)
                    } else {
                        onBackRequest()
                    }
                },
                content = {
                    Text(
                        text = stringResource(Res.string.details_screen_button_save),
                        color = Color.White,
                    )
                }
            )
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small),
                onClick = {
                    onBackRequest()
                },
                content = {
                    Text(
                        text = "Отмена",
                        color = Color.White,
                    )
                }
            )
        },
        content = {
            DatePicker(
                state = state
            )
        }
    )
}