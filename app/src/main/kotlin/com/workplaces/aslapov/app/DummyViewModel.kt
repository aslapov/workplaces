package com.workplaces.aslapov.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

class DummyViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _mainFormState = MutableLiveData<DummyViewState>()
    val dummyFormState: LiveData<DummyViewState> = _mainFormState

    fun onLogout() {
        viewModelScope.launch {
            try {
                userRepository.logout()
                _mainFormState.value = DummySuccess
            } catch (e: ConnectException) {
                e.message
                _mainFormState.value = DummyError(R.string.dummy_logout_error)
            }
        }
    }
}

sealed class DummyViewState
object DummySuccess : DummyViewState()
data class DummyError(val errorStringId: Int) : DummyViewState()
