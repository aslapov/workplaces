package com.workplaces.aslapov.app

import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.UserRepository
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(
    @Named("Network") private val userRepository: UserRepository
) : BaseViewModel() {
    fun isUserLoggedIn(): Boolean = userRepository.isUserLoggedIn()
}
