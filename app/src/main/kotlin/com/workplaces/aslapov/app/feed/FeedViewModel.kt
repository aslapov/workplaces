package com.workplaces.aslapov.app.feed

import androidx.lifecycle.viewModelScope
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

    init {
        createInitialState()
        observeViewState()
    }

    fun onPostLikeClicked(post: Post) {
        viewModelScope.launch {
            state = try {
                if (post.liked) {
                    feedUseCase.removeLike(post)
                } else {
                    feedUseCase.like(post)
                }
                FeedViewState.Content(feedUseCase.getFeed())
            } catch (e: FeedException) {
                Timber.tag(TAG).d(e)
                FeedViewState.Error
            }
        }
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
