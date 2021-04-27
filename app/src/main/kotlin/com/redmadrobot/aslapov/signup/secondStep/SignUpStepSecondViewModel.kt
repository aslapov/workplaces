package com.redmadrobot.aslapov.signup.secondStep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val MAX_LENGTH = 5

class SignUpStepSecondViewModel @Inject constructor() {
    private val _signUpStepSecondViewState = MutableLiveData<SignUpStepSecondViewState>()
    val signUpStepSecondViewState: LiveData<SignUpStepSecondViewState> = _signUpStepSecondViewState

    fun validateInput(firstname: String, lastname: String, birthday: String) {
        when {
            firstname.length < MAX_LENGTH -> {
                _signUpStepSecondViewState.value = SignUpStepSecondError("Username has to be longer than 4 characters")
            }
            lastname.length < MAX_LENGTH -> {
                _signUpStepSecondViewState.value = SignUpStepSecondError("Username has to be longer than 4 characters")
            }
            birthday.length < MAX_LENGTH -> {
                _signUpStepSecondViewState.value = SignUpStepSecondError("Password has to be longer than 4 characters")
            }
            else -> {
                _signUpStepSecondViewState.value = SignUpStepSecondSuccess
            }
        }
    }
}

sealed class SignUpStepSecondViewState
object SignUpStepSecondSuccess : SignUpStepSecondViewState()
data class SignUpStepSecondError(val error: String) : SignUpStepSecondViewState()
