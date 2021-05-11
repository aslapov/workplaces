package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.domain.UserRepository
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class UserAuthenticator @Inject constructor(
    @Named("Network") private val repository: Lazy<UserRepository>
) : Authenticator {

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TAG = "UserAuthenticator"
    }

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        val currentAccessToken = repository.get().accessToken
        val header = response.request.header(HEADER_AUTHORIZATION)
        val requestAccessToken = header?.substring("Bearer ".length)

        return if (currentAccessToken == requestAccessToken) {
            Timber.tag(TAG).i("Token refreshing")
            val newAccessToken = refreshTokenSynchronously()
            newAccessToken?.let { buildRequestWithNewAccessToken(response, it) }
        } else {
            Timber.tag(TAG).i("Proceeding with current token")
            buildRequestWithNewAccessToken(response, requireNotNull(currentAccessToken))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private fun refreshTokenSynchronously(): String? {
        return runBlocking {
            try {
                repository.get().refreshToken()
            } catch (exception: Exception) {
                Timber.tag(TAG).d(exception, "An error occurred while token updating")
                repository.get().logout()
                null
            }
        }
    }

    private fun buildRequestWithNewAccessToken(response: Response, newAccessToken: String): Request {
        return response.request
            .newBuilder()
            .header(HEADER_AUTHORIZATION, "Bearer $newAccessToken")
            .build()
    }
}
