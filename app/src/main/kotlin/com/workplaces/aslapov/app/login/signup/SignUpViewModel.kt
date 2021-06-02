package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.domain.login.UserCredentials
import com.workplaces.aslapov.domain.login.signup.SignUpException
import com.workplaces.aslapov.domain.login.signup.SignUpUseCase
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<SignUpViewState>() {

    val isLoading = viewState.mapDistinct { it.isLoading }

    init {
        state = createInitialState()
    }

    var email: String = ""
        private set

    var password: String = ""
        private set

    fun onGoNextClicked(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun onSignUpClicked(firstname: String, lastname: String, nickname: String, birthday: String) {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val userCredentials = UserCredentials(email, password)
                signUpUseCase.signUp(
                    userCredentials = userCredentials,
                    firstName = firstname,
                    lastName = lastname,
                    nickName = nickname,
                    birthDay = LocalDate.parse(birthday, dateTimeFormatter),
                )
                navigateTo(SignUpStepTwoFragmentDirections.signUpToWelcomeAction())
            } catch (e: SignUpException) {
                eventsQueue.offerEvent(MessageEvent(e.messageId))
            } finally {
                state = state.copy(isLoading = false)
            }
        }
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
