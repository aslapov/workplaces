package com.workplaces.aslapov.app.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.data.UserRepositoryImpl
import com.workplaces.aslapov.domain.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userRepository: UserRepositoryImpl) : BaseViewModel() {

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult
    var email: String = ""
        private set
    var password: String = ""
        private set
    private var firstname: String = ""
    private var lastname: String = ""
    private var nickname: String = ""
    private var birthday: String = ""
    fun isUserDataStepFirstValid(): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun onGoNextClicked(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun onSignUpClicked(firstname: String, lastname: String, nickname: String, birthday: String) {
        this.nickname = nickname
        this.firstname = firstname
        this.lastname = lastname
        this.birthday = birthday
        viewModelScope.launch {
            when (userRepository.register(email, password)) {
                is ResponseResultSuccess -> updateUser()
                is ResponseResultError -> _signUpResult.value = SignUpFail("Ошибка регистрации")
            }
        }
    }
    private suspend fun updateUser() {
        val user = User(
            firstName = firstname,
            lastName = lastname,
            nickName = nickname,
            birthday = birthday,
            avatarUrl = null
        )
        when (userRepository.updateUser(user)) {
            is ResponseResultSuccess -> _signUpResult.value = SignUpRegistered
            is ResponseResultError -> _signUpResult.value = SignUpFail("Ошибка регистрации")
        }
    }
}

sealed class SignUpResult
object SignUpRegistered : SignUpResult()
data class SignUpFail(val error: String) : SignUpResult()
