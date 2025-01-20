package org.magomedov.listcase.data.local

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope
import org.magomedov.listcase.data.local.db.ListCaseDatabase


expect fun Scope.sqlDriverFactory(): SqlDriver

fun createDatabase(driver: SqlDriver): ListCaseDatabase {
    val database = ListCaseDatabase(driver)
    return database
}