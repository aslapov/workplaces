package com.workplaces.aslapov.app.feed

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.MainGraphDirections
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.feed.FeedException
import com.workplaces.aslapov.domain.feed.FeedUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedUseCase: FeedUseCase,
) : BaseViewModel<FeedViewState>() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    fun onPostLikeClicked(post: PostInfo) {
        viewModelScope.launch {
            state = try {
                if (post.liked) {
                    feedUseCase.removeLike(post.id)
                } else {
                    feedUseCase.like(post.id)
                }
                FeedViewState.Content(feedUseCase.getFeed().map { toPostInfo(it) })
            } catch (e: FeedException) {
                Timber.tag(TAG).d(e)
                FeedViewState.Error
            }
        }
    }

    fun onRefreshClicked() {
        observeViewState()
    }

    fun onFindFriendsClicked() {
        navigateTo(MainGraphDirections.toFindFriendsGraph())
    }

    private fun observeViewState() {
        state = FeedViewState.Loading
        viewModelScope.launch {
            state = try {
                val posts = feedUseCase.getFeed().map { toPostInfo(it) }

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
}

sealed class FeedViewState {
    object Empty : FeedViewState()
    object Loading : FeedViewState()
    object Error : FeedViewState()
    data class Content(
        val posts: List<PostInfo>
    ) : FeedViewState()
}
