package org.magomedov.listcase.di

import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration

object Koin {
    var di: KoinApplication? = null

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration)
        }
    }
}