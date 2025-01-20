package org.magomedov.listcase.data.local.di

import org.koin.dsl.module
import org.magomedov.listcase.data.local.db.TaskDao
import org.magomedov.listcase.data.local.createDatabase
import org.magomedov.listcase.data.local.sqlDriverFactory

val databaseModule = module {
    factory { sqlDriverFactory() }
    single { createDatabase(driver = get()) }
    single { TaskDao(listCaseDatabase = get()) }
}