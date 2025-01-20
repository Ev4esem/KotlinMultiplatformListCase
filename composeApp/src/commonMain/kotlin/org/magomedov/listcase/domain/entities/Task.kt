package org.magomedov.listcase.domain.entities

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Task(
    val id: Long = Random.nextLong(),
    val title: String,
    val date: String?,
    val importance: Importance = Importance.Default,
    val isReady: Boolean = false,
)
