package com.workplaces.aslapov.app.findfriends

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.google.android.material.card.MaterialCardView
import com.redmadrobot.extensions.viewbinding.inflateViewBinding
import com.workplaces.aslapov.databinding.UserItemBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class UserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding: UserItemBinding = inflateViewBinding()

    @TextProp
    fun setFullName(name: CharSequence) {
        binding.userFullName.text = name
    }

    @TextProp
    fun setNickName(name: CharSequence) {
        binding.userNickname.text = name
    }

    @ModelProp
    fun setAction(actionResId: Int) {
        binding.userAction.setImageResource(actionResId)
    }

    @CallbackProp
    fun setOnActionClickListener(listener: OnClickListener?) {
        binding.userAction.setOnClickListener(listener)
    }
}
