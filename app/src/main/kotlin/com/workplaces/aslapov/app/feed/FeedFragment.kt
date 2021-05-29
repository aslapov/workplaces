package com.workplaces.aslapov.app.feed

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.feed.Post

class FeedFragment : BaseFragment(R.layout.feed_fragment) {

    private val feedViewModel: FeedViewModel by viewModels { viewModelFactory }

    private val feed: LinearLayout get() = requireView().findViewById(R.id.feed_layout)
    private val progress: LinearLayout get() = requireView().findViewById(R.id.feed_progress_layout)
    private val empty: RelativeLayout get() = requireView().findViewById(R.id.feed_empty_layout)
    private val error: RelativeLayout get() = requireView().findViewById(R.id.feed_error_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(feedViewModel.viewState, ::onStateChanged)
        observe(feedViewModel.empty, ::onEmpty)
        observe(feedViewModel.error, ::onError)
        observe(feedViewModel.loading, ::onLoading)
        observe(feedViewModel.eventsQueue, ::onEvent)
    }

    private fun onStateChanged(state: FeedViewState) {
        if (state is FeedViewState.Content) {
            feed.isVisible = true
            showContent(state.posts)
        } else {
            feed.isVisible = false
        }
    }

    private fun onEmpty(isEmpty: Boolean) {
        empty.isVisible = isEmpty
    }

    private fun onError(isError: Boolean) {
        error.isVisible = isError
    }

    private fun onLoading(isLoading: Boolean) {
        progress.isVisible = isLoading
    }

    private fun showContent(posts: List<Post>) {}
}
