package com.workplaces.aslapov.app.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

class ProfileEditViewModel @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "ProfileEditViewModel"
    }

    private val liveState = MutableLiveData(createInitialState())
    private var state: ProfileEditViewState by liveState.delegate()
    val viewState: LiveData<ProfileEditViewState> = liveState

    fun onFirstNameEntered(firstName: String) {
        if (isFirstnameValid(firstName)) {
            state = state.copy(firstName = firstName, isFirstNameValid = true)
        } else {
            state = state.copy(firstName = firstName, isFirstNameValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.profile_firstname_error))
        }
        checkSaveButtonEnable()
    }

    fun onLastNameEntered(lastName: String) {
        if (isLastnameValid(lastName)) {
            state = state.copy(lastName = lastName, isLastNameValid = true)
        } else {
            state = state.copy(lastName = lastName, isLastNameValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.profile_lastname_error))
        }
        checkSaveButtonEnable()
    }

    fun onNickNameEntered(nickName: String) {
        if (isNicknameValid(nickName)) {
            state = state.copy(nickName = nickName, isNickNameValid = true)
        } else {
            state = state.copy(nickName = nickName, isNickNameValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.profile_nickname_error))
        }
        checkSaveButtonEnable()
    }

    fun onBirthDayEntered(birthDay: String) {
        if (isBirthdayValid(birthDay)) {
            state = state.copy(birthDay = birthDay, isBirthDayValid = true)
        } else {
            state = state.copy(birthDay = birthDay, isBirthDayValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.profile_birthday_error))
        }
        checkSaveButtonEnable()
    }

    fun onBackClicked() {
        navigateTo(ProfileEditFragmentDirections.profileEditToProfileAction())
    }

    fun onSaveClicked(firstname: String, lastname: String, nickname: String, birthday: String) {
        viewModelScope.launch {
            val user = User(
                firstName = firstname,
                lastName = lastname,
                nickName = nickname,
                birthday = LocalDate.parse(birthday, formatter),
                avatarUrl = userRepository.getMyUser().avatarUrl
            )
            try {
                userRepository.updateUser(user)
                navigateTo(ProfileEditFragmentDirections.profileEditToProfileAction())
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

    private fun createInitialState(): ProfileEditViewState {
        val user = requireNotNull(userRepository.user)
        val firstname = user.firstName
        val lastName = user.lastName
        val nickName = user.nickName.orEmpty()
        val birthDay = user.birthday.format(formatter)
        return ProfileEditViewState(
            firstName = firstname,
            isFirstNameValid = isFirstnameValid(firstname),
            lastName = lastName,
            isLastNameValid = isLastnameValid(lastName),
            nickName = nickName,
            isNickNameValid = isNicknameValid(nickName),
            birthDay = birthDay,
            isBirthDayValid = isBirthdayValid(birthDay),
            isSaveButtonEnabled = false,
            isLoading = false
        )
    }

    private fun checkSaveButtonEnable() {
        val user = requireNotNull(userRepository.user)
        val isUserFieldsValid = state.isFirstNameValid &&
            state.isLastNameValid &&
            state.isNickNameValid &&
            state.isBirthDayValid
        val isUserFieldsChanged = state.firstName != user.firstName ||
            state.lastName != user.lastName ||
            state.nickName != user.nickName ||
            state.birthDay != user.birthday.format(formatter)
        state = state.copy(isSaveButtonEnabled = isUserFieldsValid && isUserFieldsChanged)
    }
}

data class ProfileEditViewState(
    val firstName: String,
    val isFirstNameValid: Boolean,
    val lastName: String,
    val isLastNameValid: Boolean,
    val nickName: String,
    val isNickNameValid: Boolean,
    val birthDay: String,
    val isBirthDayValid: Boolean,
    val isSaveButtonEnabled: Boolean,
    val isLoading: Boolean,
)
