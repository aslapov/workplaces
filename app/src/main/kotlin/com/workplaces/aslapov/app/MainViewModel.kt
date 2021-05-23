package com.workplaces.aslapov.app

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) : BaseViewModel<Nothing>() {

    init {
        viewModelScope.launch {
            authRepository.logoutEvent
                .collect {
                    if (it) {
                        navigateAction(R.id.to_auth_graph_action)
                    }
                }
        }
    }

    fun isUserLoggedIn() = authRepository.isUserLoggedIn()
}
