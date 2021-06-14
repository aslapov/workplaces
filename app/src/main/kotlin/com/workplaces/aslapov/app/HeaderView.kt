package com.workplaces.aslapov.app

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.google.android.material.appbar.MaterialToolbar
import com.redmadrobot.extensions.viewbinding.inflateViewBinding
import com.workplaces.aslapov.databinding.FeedHeaderBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialToolbar(context, attrs, defStyleAttr) {

    private val binding: FeedHeaderBinding = inflateViewBinding()

    @TextProp
    fun setHeadTitle(title: CharSequence) {
        binding.feedHeader.text = title
    }
}
