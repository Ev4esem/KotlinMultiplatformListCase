package org.magomedov.listcase.data.mapper

import org.magomedov.listcase.domain.entities.Importance
import org.magomedov.listcase.domain.entities.Task
import orgmagomedovlistcase.TaskEntity

fun Task.toDataLayer() = TaskEntity(
    id = id,
    title = title,
    date = date,
    importance = importance.toDataLayer(),
    isReady = isReady.toDataLater(),
)

fun Importance.toDataLayer() = when(this) {
    Importance.Default -> "default"
    Importance.Low -> "low"
    Importance.High -> "high"
}

fun Boolean.toDataLater(): Long = if (this) 1 else 0

fun TaskEntity.toDomainLayer() = Task(
    id = id,
    title = title,
    date = date,
    importance = importance.toDomainLayer(),
    isReady = isReady.toDomainLayer(),
)

fun Long.toDomainLayer() = this == 1L

fun String.toDomainLayer() = when(this) {
    "low" -> Importance.Low
    "high" -> Importance.High
    else -> Importance.Default
}

fun List<TaskEntity>.toDomainLayer(): List<Task> {
    return map { it.toDomainLayer() }
}