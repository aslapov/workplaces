package com.redmadrobot.aslapov.signup.firstStep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val MAX_LENGTH = 5

class SignUpStepFirstViewModel @Inject constructor() {

    private val _signUpStepFirstViewState = MutableLiveData<SignUpStepFirstViewState>()
    val signUpStepFirstViewState: LiveData<SignUpStepFirstViewState> = _signUpStepFirstViewState

    fun validateInput(nickname: String, email: String, password: String) {
        when {
            nickname.length < MAX_LENGTH -> {
                _signUpStepFirstViewState.value = SignUpStepFirstError("Username has to be longer than 4 characters")
            }
            email.length < MAX_LENGTH -> {
                _signUpStepFirstViewState.value = SignUpStepFirstError("Username has to be longer than 4 characters")
            }
            password.length < MAX_LENGTH -> {
                _signUpStepFirstViewState.value = SignUpStepFirstError("Password has to be longer than 4 characters")
            }
            else -> {
                _signUpStepFirstViewState.value = SignUpStepFirstSuccess
            }
        }
    }
}

sealed class SignUpStepFirstViewState
object SignUpStepFirstSuccess : SignUpStepFirstViewState()
data class SignUpStepFirstError(val error: String) : SignUpStepFirstViewState()
