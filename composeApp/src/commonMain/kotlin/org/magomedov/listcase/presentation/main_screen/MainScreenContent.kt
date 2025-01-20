package org.magomedov.listcase.presentation.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinmultiplatformlistcase.composeapp.generated.resources.Res
import kotlinmultiplatformlistcase.composeapp.generated.resources.ic_add
import kotlinmultiplatformlistcase.composeapp.generated.resources.main_screen_title
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.magomedov.listcase.navigation.NavigationScreen
import org.magomedov.listcase.presentation.main_screen.composable.TaskItemList
import org.magomedov.listcase.presentation.theme.robotoFamily

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun MainScreenContent(
    navigator: Navigator,
    mainViewModel: MainViewModel = MainViewModel(),
) {
    val uiState by mainViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.init()
    }

    AdaptiveScaffold(
        topBar = {
            Box(
                modifier = Modifier.padding(horizontal = 10.dp),
            ) {
                Text(
                    text = stringResource(Res.string.main_screen_title),
                    fontSize = MaterialTheme.typography.h2.fontSize,
                    fontFamily = robotoFamily(),
                    fontWeight = FontWeight.Medium,
                )
            }
        },
        content = { paddingValues ->
            TaskItemList(
                modifier = Modifier.padding(paddingValues),
                tasks = uiState.tasks,
                onChangeTaskStatus = {
                    mainViewModel.handlerIntent(MainIntent.ChangeTaskStatus(it))
                },
                onClickTask = {
                    navigator.navigate(NavigationScreen.EditScreen.route.plus("/${it.id}"))
                },
                onDeleteItem = {
                    mainViewModel.handlerIntent(MainIntent.DeleteTask(it))
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.Blue,
                onClick = {
                    navigator.navigate(NavigationScreen.AddScreen.route)
                },
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_add),
                        tint = Color.White,
                        contentDescription = null,
                    )
                }
            )
        }
    )
}