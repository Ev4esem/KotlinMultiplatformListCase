package org.magomedov.listcase.presentation.details_screen

import org.magomedov.listcase.domain.entities.Importance

data class ImportanceItem(
    val title: String,
    val importance: Importance,
) {
    companion object {
        val importanceList = listOf(
            ImportanceItem(
                title = "Низкий",
                importance = Importance.Low,
            ),
            ImportanceItem(
                title = "Нет",
                importance = Importance.Default,
            ),
            ImportanceItem(
                title = "Высокий",
                importance = Importance.High,
            ),
        )
    }
}
