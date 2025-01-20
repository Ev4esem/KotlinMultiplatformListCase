package org.magomedov.listcase.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.CupertinoThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.MaterialThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.magomedov.listcase.System
import org.magomedov.listcase.navigation.NavGraph
import org.magomedov.listcase.platform
import org.magomedov.listcase.presentation.details_screen.add.AddDetailsContent
import org.magomedov.listcase.presentation.details_screen.edit.EditDetailsContent
import org.magomedov.listcase.presentation.main_screen.MainScreenContent

@Composable
@Preview
fun App() {
    PreComposeApp {
        val navigator = rememberNavigator()
        MaterialTheme {
            AppTheme(
                theme = if(platform().system == System.ANDROID) Theme.Material3 else Theme.Cupertino,
            ) {
                NavGraph(
                    navigator = navigator,
                    onMainScreen = {
                        MainScreenContent(navigator)
                    },
                    onEditScreen = {
                        EditDetailsContent(
                           navigator = navigator,
                           platform = platform(),
                           taskId = it
                        )
                    },
                    onAddScreen = {
                        AddDetailsContent(
                           navigator = navigator,
                           platform = platform()
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppTheme(
    theme: Theme,
    content: @Composable () -> Unit,
) {
    AdaptiveTheme(
        target = theme,
        content = content,
        material = MaterialThemeSpec.Default(),
        cupertino = CupertinoThemeSpec.Default()
    )
}