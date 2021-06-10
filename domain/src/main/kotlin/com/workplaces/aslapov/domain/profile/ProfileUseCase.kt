package com.workplaces.aslapov.domain.profile

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class ProfileUseCase @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository,
    @RepositoryInUse private val userRepository: UserRepository,
) {

    companion object {
        private const val TAG = "ProfileUseCase"
    }

    fun getCurrentProfile(): Flow<User> {
        return userRepository.user
            .filterNotNull()
            .onStart { userRepository.getCurrentUser() }
            .catch { handleError(it) }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun updateProfile(
        firstName: String,
        lastName: String,
        nickName: String,
        avatarUrl: String?,
        birthDay: LocalDate,
    ) {
        try {
            userRepository.updateUser(
                firstName = firstName,
                lastName = lastName,
                nickName = nickName,
                avatarUrl = avatarUrl,
                birthDay = birthDay,
            )
        } catch (error: Throwable) {
            Timber.tag(TAG).d(error)
            handleError(error)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun logout() {
        try {
            authRepository.logout()
        } catch (e: Throwable) {
            Timber.tag(TAG).d(e)
        } finally {
            userRepository.logout()
        }
    }

    private fun handleError(error: Throwable) {
        val messageId = when (error) {
            is NetworkException -> getExceptionMessageIdByCode(error.code)
            is UnknownHostException -> R.string.profile_network_connection_error
            is SSLPeerUnverifiedException -> R.string.profile_pinning_failure
            is SSLHandshakeException -> R.string.profile_pinning_failure
            else -> R.string.profile_fail
        }
        throw ProfileException(messageId)
    }

    private fun getExceptionMessageIdByCode(code: ErrorCode): Int {
        return when (code) {
            ErrorCode.BAD_FILE_EXTENSION_ERROR -> R.string.profile_bad_file_extension_error
            ErrorCode.FILE_NOT_FOUND_ERROR -> R.string.profile_file_not_found_error
            ErrorCode.TOO_BIG_FILE_ERROR -> R.string.profile_too_big_file_error
            ErrorCode.INVALID_TOKEN -> R.string.profile_invalid_token
            else -> R.string.profile_fail
        }
    }
}
