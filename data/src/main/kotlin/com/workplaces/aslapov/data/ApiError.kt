package com.workplaces.aslapov.data

import com.squareup.moshi.JsonClass
import com.workplaces.aslapov.domain.ErrorCode

@JsonClass(generateAdapter = true)
data class ApiError(
    val message: String,
    val code: ErrorCode
)
