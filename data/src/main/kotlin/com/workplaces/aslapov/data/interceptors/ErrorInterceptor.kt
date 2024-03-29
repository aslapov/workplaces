package com.workplaces.aslapov.data.interceptors

import com.squareup.moshi.Moshi
import com.workplaces.aslapov.data.ApiError
import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    private val moshi: Moshi
) : Interceptor {

    @Suppress("TooGenericExceptionCaught")
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            val responseBody = requireNotNull(response.body?.string())
            val error = try {
                moshi.adapter(ApiError::class.java)
                    .fromJson(responseBody)
            } catch (e: Throwable) {
                val exception = NetworkException(e.message, ErrorCode.UNKNOWN_ERROR, e.cause)
                throw exception
            }

            requireNotNull(error)
            throw NetworkException(error.message, error.code)
        }

        return response
    }
}
