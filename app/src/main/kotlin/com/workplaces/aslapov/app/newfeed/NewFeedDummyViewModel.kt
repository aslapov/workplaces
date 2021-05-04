package com.workplaces.aslapov.app.newfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

class NewFeedDummyViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _newFeedDummyFormState = MutableLiveData<NewFeedDummyViewState>()
    val newFeedDummyFormState: LiveData<NewFeedDummyViewState> = _newFeedDummyFormState

    fun onLogout() {
        viewModelScope.launch {
            try {
                userRepository.logout()
                _newFeedDummyFormState.value = NewFeedDummySuccess
            } catch (e: ConnectException) {
                e.message
                _newFeedDummyFormState.value = NewFeedDummyError(R.string.dummy_logout_error)
            }
        }
    }
}

sealed class NewFeedDummyViewState
object NewFeedDummySuccess : NewFeedDummyViewState()
data class NewFeedDummyError(val errorStringId: Int) : NewFeedDummyViewState()
