package com.redmadrobot.aslapov.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _mainFormState = MutableLiveData<MainViewState>()
    val mainFormState: LiveData<MainViewState> = _mainFormState

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _mainFormState.value = MainSuccess
        }
    }
}

sealed class MainViewState
object MainSuccess : MainViewState()
data class MainError(val error: String) : MainViewState()
