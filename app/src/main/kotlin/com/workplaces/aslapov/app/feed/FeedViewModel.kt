package com.workplaces.aslapov.app.feed

import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.feed.FeedException
import com.workplaces.aslapov.domain.feed.FeedUseCase
import com.workplaces.aslapov.domain.feed.Post
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedUseCase: FeedUseCase,
) : BaseViewModel<FeedViewState>() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    val empty = viewState.mapDistinct { it is FeedViewState.Empty }
    val loading = viewState.mapDistinct { it is FeedViewState.Loading }
    val error = viewState.mapDistinct { it is FeedViewState.Error }

    init {
        createInitialState()
        observeViewState()
    }

    private fun observeViewState() {
        viewModelScope.launch {
            state = try {
                val posts = feedUseCase.getFeed()

                if (posts.isEmpty()) {
                    FeedViewState.Empty
                } else {
                    FeedViewState.Content(posts)
                }
            } catch (e: FeedException) {
                Timber.tag(TAG).d(e)
                FeedViewState.Error
            }
        }
    }

    private fun createInitialState() {
        state = FeedViewState.Loading
    }
}

sealed class FeedViewState {
    object Empty : FeedViewState()
    object Loading : FeedViewState()
    object Error : FeedViewState()
    data class Content(
        val posts: List<Post>
    ) : FeedViewState()
}
