package com.workplaces.aslapov.app.login.signin

import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.AuthRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    @RepositoryInUse private val authRepository: AuthRepository
) : BaseViewModel<SignInViewState>() {

    companion object {
        private const val TAG = "SignInViewModel"
    }

    val email = viewState.mapDistinct { it.email }
    val password = viewState.mapDistinct { it.password }
    val isNextButtonEnabled = viewState.mapDistinct { it.email.isValid && it.password.isValid }
    val isLoading = viewState.mapDistinct { it.isLoading }

    init {
        viewState.value = createInitialState()
    }

    fun onEmailEntered(email: String) {
        state = if (email.isNotEmpty()) {
            state.copy(email = SignInFieldState(email, true, null))
        } else {
            state.copy(email = SignInFieldState(email, false, R.string.sign_in_email_invalid))
        }
    }

    fun onPasswordEntered(password: String) {
        state = if (password.isNotEmpty()) {
            state.copy(password = SignInFieldState(password, true, null))
        } else {
            state.copy(password = SignInFieldState(password, false, R.string.sign_in_password_invalid))
        }
    }

    fun onSignInClicked() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                authRepository.login(state.email.value, state.password.value)
                navigateTo(SignInFragmentDirections.signInToWelcomeAction())
            } catch (e: NetworkException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(MessageEvent(R.string.invalid_credentials))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(MessageEvent(R.string.sign_in_network_connecction_error))
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun onRegisterClicked() { navigateTo(SignInFragmentDirections.signInToSignUpAction()) }

    fun onBackClicked() { navigateUp() }

    private fun createInitialState(): SignInViewState {
        return SignInViewState(
            email = SignInFieldState("", false, null),
            password = SignInFieldState("", false, null),
            isLoading = false,
        )
    }
}

data class SignInViewState(
    val email: SignInFieldState,
    val password: SignInFieldState,
    val isLoading: Boolean,
)

data class SignInFieldState(
    val value: String,
    val isValid: Boolean,
    val errorId: Int?,
)
