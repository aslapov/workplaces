package com.workplaces.aslapov.app.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import com.workplaces.aslapov.domain.ResponseResultError
import com.workplaces.aslapov.domain.ResponseResultSuccess
import com.workplaces.aslapov.domain.UserRepository
import com.workplaces.aslapov.domain.isEmailValid
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialState())
    private var state: SignInViewState by liveState.delegate()
    val isNextButtonEnabled = liveState.mapDistinct { it.isNextButtonEnabled }

    fun onEmailEntered(email: String) {
        if (isEmailValid(email)) {
            state = state.copy(email = email, isEmailValid = true)
        } else {
            state = state.copy(email = email, isEmailValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_in_email_invalid))
        }
        checkNextButtonEnable()
    }

    fun onPasswordEntered(password: String) {
        if (password.isNotEmpty()) {
            state = state.copy(password = password, isPasswordValid = true)
        } else {
            state = state.copy(password = password, isPasswordValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_in_password_invalid))
        }
        checkNextButtonEnable()
    }
    fun onSignInClicked() {
        viewModelScope.launch {
            when (userRepository.login(state.email, state.password)) {
                is ResponseResultSuccess -> eventsQueue.offerEvent(SignInSuccessEvent)
                is ResponseResultError -> eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_in_signin_fail))
            }
        }
    }
    private fun createInitialState(): SignInViewState {
        return SignInViewState(
            email = "",
            isEmailValid = false,
            password = "",
            isPasswordValid = false,
            isNextButtonEnabled = false
        )
    }

    private fun checkNextButtonEnable() {
        val isNextButtonEnabled = state.isEmailValid && state.isPasswordValid
        state = state.copy(isNextButtonEnabled = isNextButtonEnabled)
    }
}

data class SignInViewState(
    val email: String,
    val isEmailValid: Boolean,
    val password: String,
    val isPasswordValid: Boolean,
    val isNextButtonEnabled: Boolean
)

object SignInSuccessEvent : Event
