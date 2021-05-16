package com.workplaces.aslapov.data

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    private val moshi: Moshi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            val responseBody = requireNotNull(response.body?.string())
            val error = moshi.adapter(ApiError::class.java)
                .fromJson(responseBody)
            requireNotNull(error)
            throw NetworkException(error.message, error.code)
        }

        return response
    }
}
