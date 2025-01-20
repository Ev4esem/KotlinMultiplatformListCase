package org.magomedov.listcase.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path

@Composable
fun NavGraph(
    navigator: Navigator,
    onMainScreen: @Composable () -> Unit,
    onAddScreen: @Composable () -> Unit,
    onEditScreen: @Composable (Long) -> Unit,
) {
    NavHost(
        navigator = navigator,
        initialRoute = NavigationScreen.MainScreen.route,
    ) {
        scene(route = NavigationScreen.MainScreen.route) {
            onMainScreen()
        }
        scene(route = NavigationScreen.AddScreen.route) {
            onAddScreen()
        }
        scene(route = NavigationScreen.EditScreen.route.plus(NavigationScreen.EditScreen.objectPath)) { backStackEntry ->
            val id: Long? = backStackEntry.path<Long>(NavigationScreen.EditScreen.objectName)
            id?.let {
                onEditScreen(it)
            }
        }
    }
}