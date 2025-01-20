package org.magomedov.listcase.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.magomedov.listcase.data.local.db.ListCaseDatabase

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(ListCaseDatabase.Schema, androidContext(), "${DatabaseConstants.name}.db")
}