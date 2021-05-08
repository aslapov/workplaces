package com.workplaces.aslapov.app.login

import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {
    fun onSignInClicked() {
        navigateTo(LoginFragmentDirections.loginToSignInAction())
    }

    fun onSignUpClicked() {
        navigateTo(LoginFragmentDirections.loginToSignUpAction())
    }
}
