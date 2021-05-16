package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.data.util.dateTimeFormatter
import com.workplaces.aslapov.domain.AuthRepository
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository,
    @RepositoryInUse private val userRepository: UserRepository
) : BaseViewModel<SignUpViewState>() {

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    val isLoading = viewState.mapDistinct { it.isLoading }

    init {
        viewState.value = createInitialState()
    }

    var email: String = ""
        private set

    var password: String = ""
        private set

    private var firstname: String = ""
    private var lastname: String = ""
    private var nickname: String = ""
    private var birthday: String = ""

    fun onGoNextClicked(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun onSignUpClicked(firstname: String, lastname: String, nickname: String, birthday: String) {
        state = state.copy(isLoading = true)

        this.nickname = nickname
        this.firstname = firstname
        this.lastname = lastname
        this.birthday = birthday

        viewModelScope.launch {
            try {
                authRepository.register(email, password)
                updateUser()
            } catch (e: NetworkException) {
                eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(MessageEvent(R.string.sign_up_network_connecction_error))
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    private suspend fun updateUser() {
        val user = User(
            firstName = firstname,
            lastName = lastname,
            nickName = nickname,
            birthday = LocalDate.parse(birthday, dateTimeFormatter),
            avatarUrl = null
        )

        userRepository.updateUser(user)
        navigateTo(SignUpStepTwoFragmentDirections.signUpToWelcomeAction())
    }

    private fun createInitialState(): SignUpViewState {
        return SignUpViewState(
            isLoading = false,
        )
    }
}

data class SignUpViewState(
    val isLoading: Boolean
)
