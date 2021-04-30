package com.workplaces.aslapov.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.data.UserRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class DummyViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
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
