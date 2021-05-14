package com.workplaces.aslapov.app.login.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.UserRepository
import com.workplaces.aslapov.domain.isEmailValid
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "SignInViewModel"
    }

    private val liveState = MutableLiveData(createInitialState())
    private var state: SignInViewState by liveState.delegate()

    val isNextButtonEnabled = liveState.mapDistinct { it.isNextButtonEnabled }
    val isLoading = liveState.mapDistinct { it.isLoading }

    fun onEmailEntered(email: String) {
        if (isEmailValid(email)) {
            state = state.copy(email = email, isEmailValid = true)
        } else {
            state = state.copy(email = email, isEmailValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.sign_in_email_invalid))
        }
        checkNextButtonEnable()
    }

    fun onPasswordEntered(password: String) {
        if (password.isNotEmpty()) {
            state = state.copy(password = password, isPasswordValid = true)
        } else {
            state = state.copy(password = password, isPasswordValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.sign_in_password_invalid))
        }
        checkNextButtonEnable()
    }

    fun onSignInClicked() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                userRepository.login(state.email, state.password)
                navigateTo(SignInFragmentDirections.signInToWelcomeAction())
            } catch (e: NetworkException) {
                eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(ErrorMessageEvent("Проверьте подключение к интернету"))
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun onRegisterClicked() {
        navigateTo(SignInFragmentDirections.signInToSignUpAction())
    }

    private fun createInitialState(): SignInViewState {
        return SignInViewState(
            email = "",
            isEmailValid = false,
            password = "",
            isPasswordValid = false,
            isNextButtonEnabled = false,
            isLoading = false
        )
    }

    private fun checkNextButtonEnable() {
        val isNextButtonEnabled = state.isEmailValid && state.isPasswordValid
        state = state.copy(isNextButtonEnabled = isNextButtonEnabled)
    }
}

data class SignInViewState(
    val email: String,
    val isEmailValid: Boolean,
    val password: String,
    val isPasswordValid: Boolean,
    val isNextButtonEnabled: Boolean,
    val isLoading: Boolean,
)
