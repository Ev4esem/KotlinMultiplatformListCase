package org.magomedov.listcase.presentation.details_screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveSwitch
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTextButton
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinmultiplatformlistcase.composeapp.generated.resources.Res
import kotlinmultiplatformlistcase.composeapp.generated.resources.details_screen_button_save
import kotlinmultiplatformlistcase.composeapp.generated.resources.details_screen_field_description_hint
import kotlinmultiplatformlistcase.composeapp.generated.resources.details_screen_title_do_before
import kotlinmultiplatformlistcase.composeapp.generated.resources.details_screen_title_importance
import kotlinmultiplatformlistcase.composeapp.generated.resources.ic_close
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.magomedov.listcase.Platform
import org.magomedov.listcase.core.AdaptiveDropdownMenu
import org.magomedov.listcase.domain.entities.Importance
import org.magomedov.listcase.presentation.details_screen.composable.DateDialog
import org.magomedov.listcase.presentation.theme.robotoFamily

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AddDetailsContent(
    navigator: Navigator,
    platform: Platform,
    addViewModel: AddViewModel = viewModel { AddViewModel() }
) {
    val uiState by addViewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            addViewModel.clear()
        }
    }

    if (uiState.isShowDialog) {
        DateDialog(
            onBackRequest = {
                addViewModel.handlerIntent(AddIntent.DismissDialog)
            },
            onSaveRequest = {
                addViewModel.handlerIntent(AddIntent.ChooseDate(it))
            }
        )
    } else {
        AdaptiveScaffold(
            modifier = Modifier.padding(16.dp),
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            navigator.goBack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close),
                            contentDescription = null,
                            tint = Color.Black,
                        )
                    }

                    AdaptiveTextButton(
                        enabled = uiState.task.title.isNotEmpty(),
                        onClick = {
                            addViewModel.handlerIntent(AddIntent.SaveTask)
                            navigator.popBackStack()
                        },
                    ) {
                        Text(
                            text = stringResource(Res.string.details_screen_button_save),
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 100.dp),
                    value = uiState.task.title,
                    onValueChange = { value ->
                        addViewModel.handlerIntent(AddIntent.ChangeDescription(value))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.details_screen_field_description_hint),
                            color = Color.Gray,
                        )
                    }
                )

                Text(
                    text = stringResource(Res.string.details_screen_title_importance),
                    fontSize = 16.sp,
                    fontFamily = robotoFamily(),
                    fontWeight = FontWeight.Bold,
                )
                Column {
                    val color = uiState.task.importance.toColor()
                    Button(
                        colors = ButtonDefaults.buttonColors(color),
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small),
                        onClick = {
                            addViewModel.handlerIntent(AddIntent.OpenDropdownMenu)
                        },
                        content = {
                            Text(
                                text = uiState.task.importance.toText(),
                                color = Color.White
                            )
                        }
                    )
                    AdaptiveDropdownMenu(
                        platform = platform,
                        expended = uiState.isExpandedDropdownMenu,
                        onDismissRequest = {
                            addViewModel.handlerIntent(AddIntent.DismissDropdownMenu)
                        },
                        content = {
                            uiState.dropdownList.forEach { importanceItem ->
                                DropdownMenuItem(
                                    onClick = {
                                        addViewModel.handlerIntent(
                                            AddIntent.ChooseDropdownItem(importanceItem.importance)
                                        )
                                    },
                                    content = {
                                        val colorDependOnImportance =
                                            importanceItem.importance.toColor()
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(
                                                    colorDependOnImportance,
                                                    MaterialTheme.shapes.small
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = importanceItem.title,
                                        )
                                    }
                                )
                            }
                        },
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(Res.string.details_screen_title_do_before),
                    fontSize = 16.sp,
                    fontFamily = robotoFamily(),
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box {
                        if (uiState.isSwitch) {
                            uiState.task.date?.let { date ->
                                Text(
                                    text = date,
                                    color = Color.Blue,
                                )
                            }
                        }
                    }
                    AdaptiveSwitch (
                        checked = uiState.isSwitch,
                        onCheckedChange = { isSwitch ->
                            addViewModel.handlerIntent(AddIntent.ChangeSwitch(isSwitch))
                        }
                    )
                }
            }
        }
    }
}

fun Importance.toText(): String {
    return when (this) {
        Importance.Default -> "Нет"
        Importance.Low -> "Низкий"
        Importance.High -> "Высокий"
    }
}

fun Importance.toColor(): Color {
    return when (this) {
        Importance.Default -> Color.Gray
        Importance.Low -> Color.Green
        Importance.High -> Color.Red
    }
}