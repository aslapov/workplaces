package com.workplaces.aslapov.app

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.RootGraphDirections
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) : BaseViewModel<Nothing>() {

    init {
        authRepository.logoutFlow
            .onEach { navigateTo(RootGraphDirections.toAuthGraphAction()) }
            .launchIn(viewModelScope)
    }

    fun isUserLoggedIn() = authRepository.isUserLoggedIn()
}
