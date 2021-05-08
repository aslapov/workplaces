package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.MutableLiveData
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import com.workplaces.aslapov.domain.isEmailValid
import com.workplaces.aslapov.domain.isPasswordValid
import javax.inject.Inject

class SignUpStepOneViewModel @Inject constructor() : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialState())
    private var state: SignUpOneViewState by liveState.delegate()
    val isNextButtonEnabled = liveState.mapDistinct { it.isNextButtonEnabled }

    fun onEmailEntered(email: String) {
        if (isEmailValid(email)) {
            state = state.copy(email = email, isEmailValid = true)
        } else {
            state = state.copy(email = email, isEmailValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_email_invalid))
        }
        checkNextButtonEnable()
    }

    fun onPasswordEntered(password: String) {
        if (isPasswordValid(password)) {
            state = state.copy(password = password, isPasswordValid = true)
        } else {
            state = state.copy(password = password, isPasswordValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_password_invalid))
        }
        checkNextButtonEnable()
    }
    fun onNextClicked() {
        navigateTo(SignUpStepOneFragmentDirections.signUpToStepTwoAction())
    }
    fun onRegisteredClicked() {
        navigateTo(SignUpStepOneFragmentDirections.signUpToSignInAction())
    }
    fun onBackClicked() {
        navigateTo(SignUpStepOneFragmentDirections.signUpToLoginAction())
    }

    private fun createInitialState(): SignUpOneViewState {
        return SignUpOneViewState(
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

data class SignUpOneViewState(
    val email: String,
    val isEmailValid: Boolean,
    val password: String,
    val isPasswordValid: Boolean,
    val isNextButtonEnabled: Boolean
)
