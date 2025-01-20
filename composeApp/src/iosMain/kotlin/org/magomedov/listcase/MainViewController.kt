package org.magomedov.listcase

import androidx.compose.ui.window.ComposeUIViewController
import moe.tlaster.precompose.navigation.rememberNavigator
import org.magomedov.listcase.di.initKoin
import org.magomedov.listcase.presentation.App
import org.magomedov.listcase.presentation.main_screen.MainScreenContent
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    initKoin()

   return ComposeUIViewController {
        App()
    }
}