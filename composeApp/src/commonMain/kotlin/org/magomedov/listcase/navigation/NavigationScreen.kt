package org.magomedov.listcase.navigation

sealed class NavigationScreen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = "",
) {
    data object MainScreen: NavigationScreen("main_screen")
    data object AddScreen: NavigationScreen("add_screen")
    data object EditScreen: NavigationScreen("edit_screen", objectName = "id", objectPath = "/{id}")
}