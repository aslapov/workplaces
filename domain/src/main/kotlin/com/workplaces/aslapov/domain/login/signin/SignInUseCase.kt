package com.workplaces.aslapov.domain.login.signin

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class SignInUseCase @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) {

    companion object {
        private const val TAG = "SignInUseCase"
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun signIn(email: String, password: String) {
        try {
            authRepository.login(email, password)
        } catch (error: Exception) {
            Timber.tag(TAG).d(error)
            throw SignInException(getErrorMessageId(error))
        }
    }

    private fun getErrorMessageId(error: Throwable): Int {
        return when (error) {
            is NetworkException -> getExceptionMessageIdByCode(error.code)
            is UnknownHostException -> R.string.sign_in_network_connection_error
            is SSLPeerUnverifiedException -> R.string.sign_in_ssl_pinning_failure
            is SSLHandshakeException -> R.string.sign_in_ssl_pinning_failure
            else -> R.string.sign_in_fail
        }
    }

    private fun getExceptionMessageIdByCode(code: ErrorCode): Int {
        return when (code) {
            ErrorCode.INVALID_CREDENTIALS -> R.string.sign_in_invalid_credentials
            ErrorCode.DUPLICATE_USER_ERROR -> R.string.sign_in_duplicate_user_error
            ErrorCode.EMAIL_VALIDATION_ERROR -> R.string.sign_in_email_validation_error
            ErrorCode.PASSWORD_VALIDATION_ERROR -> R.string.sign_in_password_validation_error
            else -> R.string.sign_in_fail
        }
    }
}
