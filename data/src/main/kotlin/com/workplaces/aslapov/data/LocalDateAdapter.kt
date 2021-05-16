package com.workplaces.aslapov.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.workplaces.aslapov.data.util.dateTimeFormatter
import java.time.LocalDate

class LocalDateAdapter {

    @FromJson
    fun fromJson(value: String?): LocalDate? {
        return LocalDate.parse(value, dateTimeFormatter)
    }

    @ToJson
    fun toJson(value: LocalDate?): String? {
        return value?.format(dateTimeFormatter)
    }
}
