package com.workplaces.aslapov.data

import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            val body = response.body
            if (body != null) {
                val json = JSONObject(body.string())
                val errorMessage = json.getString("message")
                val errorCode = json.getString("code")
                throw NetworkException(errorMessage, errorCode)
            }
        }
        return response
    }
}

class NetworkException(message: String, code: String) : Exception(message)

data class ErrorResponse(
    val message: String,
    val code: String,
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
