package org.magomedov.listcase

import kotlinx.coroutines.CoroutineDispatcher

interface ListCaseDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

expect val listCaseDispatchers: ListCaseDispatchers