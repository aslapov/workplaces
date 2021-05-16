package com.workplaces.aslapov.app

import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.AuthRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) : BaseViewModel<Nothing>() {

    fun isUserLoggedIn() = authRepository.isUserLoggedIn()
}
