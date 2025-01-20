package org.magomedov.listcase.core

interface IntentHandler<T> {
    fun handlerIntent(intent: T)
}