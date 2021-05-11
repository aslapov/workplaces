package com.workplaces.aslapov.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class UuidAdapter {

    @FromJson
    fun fromJson(value: String?): UUID? {
        return UUID.fromString(value)
    }

    @ToJson
    fun toJson(value: UUID?): String? {
        return value?.toString()
    }
}
