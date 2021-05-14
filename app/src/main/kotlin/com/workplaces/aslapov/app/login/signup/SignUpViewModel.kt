package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    private val liveState = MutableLiveData(createInitialState())
    private var state: SignUpViewState by liveState.delegate()
    val isLoading = liveState.mapDistinct { it.isLoading }

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
                userRepository.register(email, password)
                updateUser()
            } catch (e: NetworkException) {
                eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(ErrorMessageEvent("Проверьте подключение к интернету"))
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
            birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
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
    val isLoading: Boolean,
)
