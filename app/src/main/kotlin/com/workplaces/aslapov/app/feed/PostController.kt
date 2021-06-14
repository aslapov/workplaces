package com.workplaces.aslapov.app.feed

import com.airbnb.epoxy.TypedEpoxyController
import com.workplaces.aslapov.R
import com.workplaces.aslapov.ResourceProvider
import java.util.*

class PostController constructor(
    private val resources: ResourceProvider,
    private val callbacks: AdapterCallbacks,
) : TypedEpoxyController<List<PostInfo>>() {

    interface AdapterCallbacks {
        fun onPostLikeClicked(post: PostInfo)
    }

    override fun buildModels(posts: List<PostInfo>) {
        val header = resources.getString(R.string.feed_header)

        headerView {
            id(header)
            title(header)
        }

        posts.forEach {
            postView {
                id(it.id)
                title(it.text)
                authorNickName(it.authorNickName)
                location(it.location)
                like(it.liked)
                image(it.imageUrl)
                clickListener { _ -> this@PostController.callbacks.onPostLikeClicked(it) }
            }
        }
    }
}
