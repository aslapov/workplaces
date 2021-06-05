package com.workplaces.aslapov.app.feed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.ApplicationResourceProvider
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.FeedFragmentBinding
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.feed.Post

class FeedFragment : BaseFragment(R.layout.feed_fragment), PostController.AdapterCallbacks {

    private val feedViewModel: FeedViewModel by viewModels { viewModelFactory }

    private val binding: FeedFragmentBinding by viewBinding()

    private lateinit var postController: PostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
        postController = PostController(ApplicationResourceProvider(requireContext()), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feedLayout.feedPosts.adapter = postController.adapter

        observe(feedViewModel.viewState, ::onStateChanged)
        observe(feedViewModel.eventsQueue, ::onEvent)
    }

    override fun onPostLikeClicked(post: Post) {
        feedViewModel.onPostLikeClicked(post)
    }

    private fun onStateChanged(state: FeedViewState) {
        binding.apply {
            feedEmptyLayout.root.isVisible = state is FeedViewState.Empty
            feedErrorLayout.root.isVisible = state is FeedViewState.Error
            feedProgressLayout.root.isVisible = state is FeedViewState.Loading

            feedLayout.root.isVisible = if (state is FeedViewState.Content) {
                postController.setData(state.posts)
                true
            } else {
                false
            }
        }
    }
}
