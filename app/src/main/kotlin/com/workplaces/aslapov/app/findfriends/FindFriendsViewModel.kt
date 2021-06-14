package com.workplaces.aslapov.app.findfriends

import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.domain.findfriends.FindFriendsException
import com.workplaces.aslapov.domain.findfriends.FindFriendsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FindFriendsViewModel @Inject constructor(
    private val findFriendsUseCase: FindFriendsUseCase,
) : BaseViewModel<FindFriendsViewState>() {

    companion object {
        private const val DEBOUNCE_MILLIS: Long = 300
    }

    private val searchWordFlow: Flow<String> = viewState.mapDistinct { it.searchWord }.asFlow()

    init {
        createInitialState()

        searchWordFlow
            .filter { it.length >= 2 }
            .debounce(DEBOUNCE_MILLIS)
            .onEach { fetchFriendsBySearchWord(it) }
            .launchIn(viewModelScope)
    }

    fun onSearchWordChanged(searchWord: String) {
        state = if (searchWord.isNotEmpty()) {
            state.copy(searchWord = searchWord, isLoading = false)
        } else {
            state.copy(searchWord = searchWord, friends = emptyList(), isLoading = false)
        }
    }

    fun onBackClicked() {
        navigateUp()
    }

    fun onCancelClicked() {
        eventsQueue.offerEvent(SetEmptySearchWordEvent())
    }

    fun onAddFriendClicked(userId: String) {
        viewModelScope.launch {
            try {
                findFriendsUseCase.addFriend(userId)
                eventsQueue.offerEvent(MessageEvent(R.string.find_friends_success_add))
            } catch (e: FindFriendsException) {
                eventsQueue.offerEvent(MessageEvent(e.messageId))
            }
        }
    }

    private suspend fun fetchFriendsBySearchWord(searchWord: String) {
        var friends: List<UserInfo> = emptyList()

        try {
            setLoadingState()
            friends = findFriendsUseCase.findFriends(searchWord)
                .map { user -> toUserInfo(user) }
        } catch (e: FindFriendsException) {
            eventsQueue.offerEvent(MessageEvent(e.messageId))
        } finally {
            state = state.copy(friends = friends, isLoading = false)
        }
    }

    private fun createInitialState() {
        state = FindFriendsViewState("", emptyList(), false)
    }

    private fun setLoadingState() {
        state = state.copy(isLoading = true)
    }
}

data class FindFriendsViewState(
    val searchWord: String,
    val friends: List<UserInfo>,
    val isLoading: Boolean,
)

class SetEmptySearchWordEvent : Event
