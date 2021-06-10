package com.workplaces.aslapov.domain.login.signup

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import com.workplaces.aslapov.domain.login.UserCredentials
import com.workplaces.aslapov.domain.profile.UserRepository
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class SignUpUseCase @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository,
    @RepositoryInUse private val userRepository: UserRepository
) {

    companion object {
        private const val TAG = "SignUpUseCase"
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun signUp(
        userCredentials: UserCredentials,
        firstName: String,
        lastName: String,
        nickName: String,
        birthDay: LocalDate,
    ) {
        try {
            authRepository.register(userCredentials.email, userCredentials.password)

            userRepository.updateUser(
                firstName = firstName,
                lastName = lastName,
                nickName = nickName,
                birthDay = birthDay,
                avatarUrl = null,
            )
        } catch (error: Exception) {
            Timber.tag(TAG).d(error)
            throw SignUpException(getErrorMessageId(error))
        }
    }

    private fun getErrorMessageId(error: Throwable): Int {
        return when (error) {
            is NetworkException -> getExceptionMessageIdByCode(error.code)
            is UnknownHostException -> R.string.sign_up_network_connection_error
            is SSLPeerUnverifiedException -> R.string.sign_up_ssl_pinning_failure
            is SSLHandshakeException -> R.string.sign_up_ssl_pinning_failure
            else -> R.string.sign_up_fail
        }
    }

    private fun getExceptionMessageIdByCode(code: ErrorCode): Int {
        return when (code) {
            ErrorCode.INVALID_CREDENTIALS -> R.string.sign_up_invalid_credentials
            ErrorCode.DUPLICATE_USER_ERROR -> R.string.sign_up_duplicate_user_error
            ErrorCode.EMAIL_VALIDATION_ERROR -> R.string.sign_up_email_validation_error
            ErrorCode.PASSWORD_VALIDATION_ERROR -> R.string.sign_up_password_validation_error
            else -> R.string.sign_up_fail
        }
    }
}
