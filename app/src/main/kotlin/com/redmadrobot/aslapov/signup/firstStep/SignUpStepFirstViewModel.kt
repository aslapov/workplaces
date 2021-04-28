package com.redmadrobot.aslapov.signup.firstStep

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val MIN_LENGTH = 5

class SignUpStepFirstViewModel @Inject constructor() {

    private val _signUpStepFirstViewState = MutableLiveData<SignUpStepFirstViewState>()
    val signUpStepFirstViewState: LiveData<SignUpStepFirstViewState> = _signUpStepFirstViewState

    fun validateInput(nickname: String, email: String, password: String) {
        when {
            !isEmailValid(email) -> {
                _signUpStepFirstViewState.value = SignUpStepFirstError("E-mail не распознан")
            }
            !isPasswordValid(password) -> {
                _signUpStepFirstViewState.value = SignUpStepFirstError(
                    "Пароль должен быть не менее 6 символов и содержать цифры и буквы нижнего и верхнего регистра"
                )
            }
            else -> {
                _signUpStepFirstViewState.value = SignUpStepFirstSuccess
            }
        }
    }

    private fun isEmailValid(email: String) = email.matches(Patterns.EMAIL_ADDRESS.toRegex())

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= MIN_LENGTH && password.matches(Regex("(.*[a-z].*)")) &&
            password.matches(Regex("(.*[A-Z].*)")) && password.matches(Regex("(.*\\d.*)"))
    }
}

sealed class SignUpStepFirstViewState
object SignUpStepFirstSuccess : SignUpStepFirstViewState()
data class SignUpStepFirstError(val error: String) : SignUpStepFirstViewState()
