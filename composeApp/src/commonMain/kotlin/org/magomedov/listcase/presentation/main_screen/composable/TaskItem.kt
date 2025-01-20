package org.magomedov.listcase.presentation.main_screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveCheckbox
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.magomedov.listcase.domain.entities.Importance
import org.magomedov.listcase.domain.entities.Task
import org.magomedov.listcase.presentation.theme.robotoFamily

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun TaskItem(
    task: Task,
    onChangeTaskStatus: () -> Unit,
    onClickTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = when(task.importance) {
        Importance.Default -> Color.Gray.copy(alpha = 0.5f)
        Importance.Low -> Color.Green.copy(alpha = 0.5f)
        Importance.High -> Color.Red.copy(alpha = 0.5f)
    }
    Row(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .clickable {
                onClickTask()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AdaptiveCheckbox(
            checked = task.isReady,
            onCheckedChange = {
                onChangeTaskStatus()
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = task.title,
                    fontFamily = robotoFamily(),
                    fontWeight = FontWeight.Medium
                )
                if (task.date != null) {
                    Text(
                        text = task.date,
                        fontSize = 12.sp,
                        fontFamily = robotoFamily(),
                        fontWeight = FontWeight.Medium,
                        color = Color.Blue,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(color, MaterialTheme.shapes.small),
            )

        }
    }
}