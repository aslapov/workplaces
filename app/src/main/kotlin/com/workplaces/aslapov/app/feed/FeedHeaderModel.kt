package com.workplaces.aslapov.app.feed

import android.view.Gravity
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.KotlinHolder

@EpoxyModelClass(layout = R.layout.feed_header)
abstract class FeedHeaderModel : EpoxyModelWithHolder<FeedHeaderHolder>() {
    @EpoxyAttribute lateinit var title: String

    override fun bind(holder: FeedHeaderHolder) {
        holder.title.text = title
        holder.title.gravity = Gravity.CENTER
    }
}

class FeedHeaderHolder : KotlinHolder() {
    val title by bind<TextView>(R.id.feed_header)
}
