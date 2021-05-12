package com.workplaces.aslapov.data

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            val responseBody = requireNotNull(response.body?.string())
            val apiErrorJsonAdapter = Moshi.Builder().build().adapter(ApiError::class.java)
            val error = apiErrorJsonAdapter.fromJson(responseBody)
            requireNotNull(error)
            throw NetworkException(error.message, error.code)
        }
        return response
    }
}
