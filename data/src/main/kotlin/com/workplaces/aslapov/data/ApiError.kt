package com.workplaces.aslapov.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    val message: String,
    val code: ErrorCode
)

enum class ErrorCode {
    INVALID_CREDENTIALS,
    INVALID_TOKEN,
    EMAIL_VALIDATION_ERROR,
    PASSWORD_VALIDATION_ERROR,
    DUPLICATE_USER_ERROR,
    SERIALIZATION_ERROR,
    FILE_NOT_FOUND_ERROR,
    TOO_BIG_FILE_ERROR,
    BAD_FILE_EXTENSION_ERROR,
    GENERIC_ERROR,
    UNKNOWN_ERROR
}
