package com.workplaces.aslapov.domain.profile

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository,
    @RepositoryInUse private val userRepository: UserRepository
) {

    companion object {
        private const val TAG = "ProfileUseCase"
    }

    fun getCurrentProfile(): Flow<User> {
        return userRepository.user
            .filter { authRepository.isUserLoggedIn() }
            .map { user -> user ?: userRepository.getCurrentUser() }
            .flowOn(Dispatchers.IO)
            .catch { handleError(it) }
    }

    suspend fun updateProfile(user: User) {
        try {
            userRepository.updateUser(user)
        } catch (e: NetworkException) {
            Timber.tag(TAG).d(e)
            throw ProfileException(getExceptionMessageIdByCode(e.code))
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).d(e)
            throw ProfileException(R.string.profile_network_connection_error)
        }
    }

    suspend fun logout() {
        authRepository.logout()
        userRepository.logout()
    }

    private fun handleError(error: Throwable) {
        Timber.tag(TAG).d(error)
        when (error) {
            is NetworkException -> throw ProfileException(getExceptionMessageIdByCode(error.code))
            is UnknownHostException -> throw ProfileException(R.string.profile_network_connection_error)
        }
    }

    private fun getExceptionMessageIdByCode(code: ErrorCode): Int {
        return when (code) {
            ErrorCode.BAD_FILE_EXTENSION_ERROR -> R.string.profile_bad_file_extension_error
            ErrorCode.FILE_NOT_FOUND_ERROR -> R.string.profile_file_not_found_error
            ErrorCode.TOO_BIG_FILE_ERROR -> R.string.profile_too_big_file_error
            else -> R.string.profile_fail
        }
    }
}
