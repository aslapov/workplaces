package com.workplaces.aslapov.data

import com.workplaces.aslapov.domain.UserRepository
import dagger.Lazy
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class TokenInterceptor @Inject constructor(
    @Named("Network") private val repository: Lazy<UserRepository>
) : Interceptor {

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TAG = "TokenInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentAccessToken = repository.get().accessToken

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
