package com.workplaces.aslapov.app.feed

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyRecyclerView
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.ApplicationResourceProvider
import com.workplaces.aslapov.LoadingView
import com.workplaces.aslapov.R
import com.workplaces.aslapov.animateLoading
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.feed.Post

class FeedFragment : BaseFragment(R.layout.feed_fragment), PostController.AdapterCallbacks {

    private val feedViewModel: FeedViewModel by viewModels { viewModelFactory }

    private val feed: LinearLayout get() = requireView().findViewById(R.id.feed_layout)
    private val progress: LinearLayout get() = requireView().findViewById(R.id.feed_progress_layout)
    private val empty: LinearLayout get() = requireView().findViewById(R.id.feed_empty_layout)
    private val error: LinearLayout get() = requireView().findViewById(R.id.feed_error_layout)
    private val loading: LoadingView get() = requireView().findViewById(R.id.loading)

    private val posts: EpoxyRecyclerView get() = requireView().findViewById(R.id.feed_posts)

    private lateinit var postController: PostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
        postController = PostController(ApplicationResourceProvider(requireContext()), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        posts.adapter = postController.adapter

        observe(feedViewModel.viewState, ::onStateChanged)
        observe(feedViewModel.empty, ::onEmpty)
        observe(feedViewModel.error, ::onError)
        observe(feedViewModel.loading, ::onLoading)
        observe(feedViewModel.eventsQueue, ::onEvent)
    }

    override fun onPostLikeClicked(post: Post) {
        feedViewModel.onPostLikeClicked(post)
    }

    private fun onStateChanged(state: FeedViewState) {
        if (state is FeedViewState.Content) {
            feed.isVisible = true
            showContent(state.posts)
        } else {
            feed.isVisible = false
        }
    }

    private fun showContent(posts: List<Post>) {
        postController.setData(posts)
    }

    private fun onEmpty(isEmpty: Boolean) {
        empty.isVisible = isEmpty
    }

    private fun onError(isError: Boolean) {
        error.isVisible = isError
    }

    private fun onLoading(isLoading: Boolean) {
        progress.isVisible = isLoading
        animateLoading(loading)
    }
}
