package com.redmadrobot.aslapov.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MAX_LENGTH = 5

class SignInViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val _signInFormState = MutableLiveData<SignInViewState>()
    val signInFormState: LiveData<SignInViewState> = _signInFormState

    private val _signInResult = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> = _signInResult

    fun userInfoChanged(email: String, password: String) {
        when {
            email.length < MAX_LENGTH -> {
                _signInFormState.value = SignInError("Username has to be longer than 4 characters")
            }
            password.length < MAX_LENGTH -> {
                _signInFormState.value = SignInError("Password has to be longer than 4 characters")
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
}

sealed class SignInViewState
object SignInSuccess : SignInViewState()
data class SignInError(val error: String) : SignInViewState()

sealed class SignInResult
object SignInAuthorized : SignInResult()
data class SignInFail(val error: String) : SignInResult()
