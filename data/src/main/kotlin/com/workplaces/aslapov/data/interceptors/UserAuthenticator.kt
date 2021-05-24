package com.workplaces.aslapov.data.interceptors

import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class UserAuthenticator @Inject constructor(
    @RepositoryInUse private val repository: AuthRepository
) : Authenticator {

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TAG = "UserAuthenticator"
    }

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        val currentAccessToken = repository.accessToken
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
                repository.refreshToken()
            } catch (exception: Exception) {
                Timber.tag(TAG).d(exception, "An error occurred while token updating")
                repository.logout()
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
