package com.workplaces.aslapov.data.interceptors

import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    @RepositoryInUse private val repository: AuthRepository
) : Interceptor {

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TAG = "TokenInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentAccessToken = repository.accessToken

        val request = if (currentAccessToken != null) {
            Timber.tag(TAG).i("Proceeding with current token")
            chain.request()
                .newBuilder()
                .header(HEADER_AUTHORIZATION, "Bearer $currentAccessToken")
                .build()
        } else {
            Timber.tag(TAG).i("Proceeding without token")
            chain.request()
        }

        return chain.proceed(request)
    }
}
