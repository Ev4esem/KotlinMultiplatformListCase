package org.magomedov.listcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.magomedov.listcase.di.initKoin
import org.magomedov.listcase.presentation.App
import org.magomedov.listcase.presentation.main_screen.MainScreenContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin {
            androidContext(applicationContext)
        }
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}