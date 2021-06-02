package com.workplaces.aslapov.app.feed

import com.airbnb.epoxy.TypedEpoxyController
import com.workplaces.aslapov.R
import com.workplaces.aslapov.ResourceProvider
import com.workplaces.aslapov.domain.feed.Post
import java.util.*

class PostController constructor(
    private val resources: ResourceProvider,
    private val callbacks: AdapterCallbacks,
) : TypedEpoxyController<List<Post>>() {

    interface AdapterCallbacks {
        fun onPostLikeClicked(post: Post)
    }

    override fun buildModels(posts: List<Post>) {
        val header = resources.getString(R.string.feed_header)

        headerView {
            id(header)
            title(header)
        }

        posts.forEach {
            post {
                id(it.id)
                title(it.text)
                author(it.author)
                like(it.liked)
                clickListener { _ -> this@PostController.callbacks.onPostLikeClicked(it) }
            }
        }
    }
}
