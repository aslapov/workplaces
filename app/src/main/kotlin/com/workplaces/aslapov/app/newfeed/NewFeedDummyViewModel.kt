package com.workplaces.aslapov.app.newfeed

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.AuthRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewFeedDummyViewModel @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) : BaseViewModel<Nothing>() {

    fun onLogout() {
        viewModelScope.launch {
            authRepository.logout()
                .collect { navigateAction(R.id.to_auth_graph_action) }
        }
    }
}
