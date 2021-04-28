package com.redmadrobot.aslapov.signin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

private const val MIN_LENGTH = 6

class SignInViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val _signInFormState = MutableLiveData<SignInViewState>()
    val signInFormState: LiveData<SignInViewState> = _signInFormState

    private val _signInResult = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> = _signInResult

    fun userInfoChanged(email: String, password: String) {
        when {
            !isEmailValid(email) -> {
                _signInFormState.value = SignInError("E-mail не распознан")
            }
            !isPasswordValid(password) -> {
                _signInFormState.value = SignInError(
                    "Пароль должен быть не менее 6 символов и содержать цифры и буквы нижнего и верхнего регистра"
                )
            }
            else -> {
                _signInFormState.value = SignInSuccess
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            when (userRepository.login(email, password)) {
                UserRepository.AuthResult.AUTHORIZED -> _signInResult.value = SignInAuthorized
                UserRepository.AuthResult.FAIL -> _signInResult.value = SignInFail("Ошибка авторизации")
            }
        }
    }

    private fun isEmailValid(email: String) = email.matches(Patterns.EMAIL_ADDRESS.toRegex())

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= MIN_LENGTH && password.matches(Regex("(.*[a-z].*)")) &&
            password.matches(Regex("(.*[A-Z].*)")) && password.matches(Regex("(.*\\d.*)"))
    }
}

sealed class SignInViewState
object SignInSuccess : SignInViewState()
data class SignInError(val error: String) : SignInViewState()

sealed class SignInResult
object SignInAuthorized : SignInResult()
data class SignInFail(val error: String) : SignInResult()
