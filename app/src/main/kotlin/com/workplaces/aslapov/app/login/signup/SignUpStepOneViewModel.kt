package com.workplaces.aslapov.app.login.signup

import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.profile.isEmailValid
import com.workplaces.aslapov.domain.profile.isPasswordValid
import javax.inject.Inject

class SignUpStepOneViewModel @Inject constructor() : BaseViewModel<SignUpOneViewState>() {

    val email = viewState.mapDistinct { it.email }
    val password = viewState.mapDistinct { it.password }
    val isNextButtonEnabled = viewState.mapDistinct { it.email.isValid && it.password.isValid }

    init {
        viewState.value = createInitialState()
    }

    fun onEmailEntered(email: String) {
        state = if (isEmailValid(email)) {
            state.copy(email = SignUpOneFieldState(email, true, null))
        } else {
            state.copy(email = SignUpOneFieldState(email, false, R.string.sign_up_email_invalid))
        }
    }

    fun onPasswordEntered(password: String) {
        state = if (isPasswordValid(password)) {
            state.copy(password = SignUpOneFieldState(password, true, null))
        } else {
            state.copy(password = SignUpOneFieldState(password, false, R.string.sign_up_password_invalid))
        }
    }

    fun onNextClicked() { navigateTo(SignUpStepOneFragmentDirections.signUpToStepTwoAction()) }

    fun onRegisteredClicked() { navigateTo(SignUpStepOneFragmentDirections.signUpToSignInAction()) }

    fun onBackClicked() { navigateUp() }

    private fun createInitialState(): SignUpOneViewState {
        return SignUpOneViewState(
            email = SignUpOneFieldState("", false, null),
            password = SignUpOneFieldState("", false, null),
        )
    }
}

data class SignUpOneViewState(
    val email: SignUpOneFieldState,
    val password: SignUpOneFieldState,
)

data class SignUpOneFieldState(
    val value: String,
    val isValid: Boolean,
    val errorId: Int?,
)
