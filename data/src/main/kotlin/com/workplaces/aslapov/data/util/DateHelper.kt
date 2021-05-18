package com.workplaces.aslapov.data.util

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun Date.convertToLocalDateViaInstant(): LocalDate {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
