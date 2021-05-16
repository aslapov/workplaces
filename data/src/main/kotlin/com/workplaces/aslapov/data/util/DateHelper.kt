package com.workplaces.aslapov.data.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun Date.convertToLocalDateViaInstant(): LocalDate {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
