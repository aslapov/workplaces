package com.redmadrobot.aslapov.start

import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.login.LoginActivity
import com.redmadrobot.aslapov.ui.MainActivity
import com.redmadrobot.aslapov.ui.base.activity.BaseActivity
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class StartViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    fun getActivityClass(): Class<out BaseActivity> =
        if (userRepository.isUserLoggedIn()) {
            MainActivity::class.java
        } else {
            LoginActivity::class.java
        }
}
