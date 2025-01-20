package org.magomedov.listcase.core

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


// ДД:ММ:ГГ
fun Long.toCalendar(): String {
    val instant = Instant.fromEpochMilliseconds(this)

    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    val day = localDate.dayOfMonth.toString().padStart(2, '0')
    val month = localDate.monthNumber.toString().padStart(2, '0')
    val year = (localDate.year % 100).toString().padStart(2, '0')

    return "$day:$month:$year"
}