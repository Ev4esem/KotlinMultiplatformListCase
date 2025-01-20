package org.magomedov.listcase.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.magomedov.listcase.data.local.di.databaseModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            databaseModule,
            mainModule
        )
    }