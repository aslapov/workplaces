package com.workplaces.aslapov.domain.login.signup

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import com.workplaces.aslapov.domain.login.UserCredentials
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository,
    @RepositoryInUse private val userRepository: UserRepository
) {

    companion object {
        private const val TAG = "SignUpUseCase"
    }

    suspend fun signUp(
        userCredentials: UserCredentials,
        firstname: String,
        lastname: String,
        nickname: String,
        birthday: String
    ) {
        try {
            authRepository.register(userCredentials.email, userCredentials.password)

            val user = User(
                firstName = firstname,
                lastName = lastname,
                nickName = nickname,
                birthday = LocalDate.parse(birthday, dateTimeFormatter),
                avatarUrl = null
            )

            userRepository.updateUser(user)
        } catch (e: NetworkException) {
            Timber.tag(TAG).d(e)
            throw SignUpException(getExceptionMessageIdByCode(e.code))
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).d(e)
            throw SignUpException(R.string.sign_up_network_connection_error)
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
