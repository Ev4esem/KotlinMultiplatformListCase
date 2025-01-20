package org.magomedov.listcase.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.scope.Scope
import org.magomedov.listcase.data.local.db.ListCaseDatabase


actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(ListCaseDatabase.Schema, "${DatabaseConstants.name}.db")
}