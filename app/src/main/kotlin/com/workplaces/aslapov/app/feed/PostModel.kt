package com.workplaces.aslapov.app.feed

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.KotlinHolder
import com.workplaces.aslapov.domain.profile.User

@EpoxyModelClass(layout = R.layout.post_item)
abstract class PostModel : EpoxyModelWithHolder<PostHolder>() {

    @EpoxyAttribute var title: String? = ""

    @EpoxyAttribute lateinit var author: User

    @EpoxyAttribute var like: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: PostHolder) {
        holder.title.text = title?.let { title }.orEmpty()
        holder.author.text = author.nickName?.let { "@${author.nickName}" }.orEmpty()

        val likeImageId = if (like) R.drawable.icon_like_pressed else R.drawable.icon_like
        holder.like.setImageResource(likeImageId)
        holder.like.setOnClickListener(clickListener)
    }
}

class PostHolder : KotlinHolder() {
    val title by bind<TextView>(R.id.post_text)
    val author by bind<TextView>(R.id.post_author)
    val like by bind<ImageView>(R.id.post_like)
}
