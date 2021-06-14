package com.workplaces.aslapov.app.feed

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import coil.load
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.redmadrobot.extensions.viewbinding.inflateViewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.databinding.PostItemBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PostView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: PostItemBinding = inflateViewBinding()

    @TextProp
    fun setTitle(title: CharSequence?) {
        binding.postText.text = title
    }

    @ModelProp
    fun setLocation(location: String) {
        binding.postLocation.text = location
    }

    @ModelProp
    fun setAuthorNickName(nickName: String?) {
        binding.postAuthor.text = nickName.orEmpty()
    }

    @ModelProp
    fun setImage(uri: String?) {
        binding.postLocationPhoto.load(uri)
    }

    @ModelProp
    fun setLike(like: Boolean) {
        val likeImageId = if (like) R.drawable.icon_like_pressed else R.drawable.icon_like
        binding.postLike.setImageResource(likeImageId)
    }

    @CallbackProp
    fun setClickListener(listener: OnClickListener?) {
        binding.postLike.setOnClickListener(listener)
    }
}
