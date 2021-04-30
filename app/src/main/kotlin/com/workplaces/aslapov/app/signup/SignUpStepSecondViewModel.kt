package com.workplaces.aslapov.app.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val MIN_LENGTH = 1

class SignUpStepSecondViewModel @Inject constructor() {
    private val _signUpStepSecondViewState = MutableLiveData<SignUpStepSecondViewState>()
    val signUpStepSecondViewState: LiveData<SignUpStepSecondViewState> = _signUpStepSecondViewState

    // Необходимо уточнить правила валидации. По умолчанию проверяется заполнение хотя бы одним символом
    fun validateInput(firstname: String, lastname: String, birthday: String) {
        when {
            firstname.length < MIN_LENGTH -> {
                _signUpStepSecondViewState.value = SignUpStepSecondError("Username has to be longer than 4 characters")
            }
            lastname.length < MIN_LENGTH -> {
                _signUpStepSecondViewState.value = SignUpStepSecondError("Username has to be longer than 4 characters")
            }
            birthday.length < MIN_LENGTH -> {
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
