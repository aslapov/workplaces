package com.workplaces.aslapov.domain.login.signin

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) {

    companion object {
        private const val TAG = "SignInUseCase"
    }

    suspend fun signIn(email: String, password: String) {
        try {
            authRepository.login(email, password)
        } catch (e: NetworkException) {
            Timber.tag(TAG).d(e)
            throw SignInException(getExceptionMessageIdByCode(e.code))
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).d(e)
            throw SignInException(R.string.sign_in_network_connection_error)
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
