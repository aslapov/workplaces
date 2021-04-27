package com.redmadrobot.aslapov.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.di.ActivityScope
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult

    var nickname: String? = null
        private set

    var email: String? = null
        private set

    var password: String? = null
        private set

    var firstname: String? = null
    var lastname: String? = null
    var birthday: String? = null

    fun setUserData(nickname: String, email: String, password: String) {
        this.nickname = nickname
        this.email = email
        this.password = password
    }

    fun updateUserData(firstname: String, lastname: String, birthday: String) {
        this.firstname = firstname
        this.lastname = lastname
        this.birthday = birthday
    }

    fun signUp() {
        viewModelScope.launch {
            when (userRepository.register(email!!, password!!)) {
                UserRepository.AuthResult.AUTHORIZED -> _signUpResult.value = SignUpAuthorized
                UserRepository.AuthResult.FAIL -> _signUpResult.value = SignUpFail("Ошибка авторизации")
            }
        }
    }
}

sealed class SignUpResult
object SignUpAuthorized : SignUpResult()
data class SignUpFail(val error: String) : SignUpResult()
