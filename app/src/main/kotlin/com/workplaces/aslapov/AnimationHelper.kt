package com.workplaces.aslapov

import android.animation.*
import android.view.animation.Animation
import androidx.core.content.ContextCompat

const val ANIMATION_DURATION: Long = 600
const val FIRST_FRACTION = 0f
const val SECOND_FRACTION = .2f
const val THIRD_FRACTION = .4f
const val FOURTH_FRACTION = .6f
const val FIFTH_FRACTION = .8f
const val LAST_FRACTION = 1f
const val PULSATION = 10

val discreteEvaluator = TypeEvaluator<Int> { fraction, startValue, _ ->
    (startValue as Number).toInt() + fraction.toInt()
}

fun animateLoading(loadingView: LoadingView) {
    val context = loadingView.context

    val pvhLeftTopBlockColor = PropertyValuesHolder.ofKeyframe(
        "leftTopBlockColor",
        Keyframe.ofInt(FIRST_FRACTION, ContextCompat.getColor(context, R.color.colorLightGreyBlue)),
        Keyframe.ofInt(SECOND_FRACTION, ContextCompat.getColor(context, R.color.colorGrey)),
        Keyframe.ofInt(THIRD_FRACTION, ContextCompat.getColor(context, R.color.colorMiddleGrey)),
        Keyframe.ofInt(FIFTH_FRACTION, ContextCompat.getColor(context, R.color.colorDarkGrey)),
        Keyframe.ofInt(LAST_FRACTION, ContextCompat.getColor(context, R.color.colorLightGreyBlue))
    )

    val leftTopBlockAnimator = ObjectAnimator.ofPropertyValuesHolder(loadingView, pvhLeftTopBlockColor)
        .apply { setLoadingAnimator(this, true) }

    val pvhRightTopBlockColor = PropertyValuesHolder.ofKeyframe(
        "rightTopBlockPaint",
        Keyframe.ofInt(FIRST_FRACTION, ContextCompat.getColor(context, R.color.colorDarkGrey)),
        Keyframe.ofInt(SECOND_FRACTION, ContextCompat.getColor(context, R.color.colorLightGreyBlue)),
        Keyframe.ofInt(THIRD_FRACTION, ContextCompat.getColor(context, R.color.colorGrey)),
        Keyframe.ofInt(FIFTH_FRACTION, ContextCompat.getColor(context, R.color.colorMiddleGrey)),
        Keyframe.ofInt(LAST_FRACTION, ContextCompat.getColor(context, R.color.colorDarkGrey))
    )

    val rightTopBlockAnimator = ObjectAnimator.ofPropertyValuesHolder(loadingView, pvhRightTopBlockColor)
        .apply { setLoadingAnimator(this, true) }

    val pvhRightBottomBlockColor = PropertyValuesHolder.ofKeyframe(
        "rightBottomBlockPaint",
        Keyframe.ofInt(FIRST_FRACTION, ContextCompat.getColor(context, R.color.colorMiddleGrey)),
        Keyframe.ofInt(SECOND_FRACTION, ContextCompat.getColor(context, R.color.colorDarkGrey)),
        Keyframe.ofInt(THIRD_FRACTION, ContextCompat.getColor(context, R.color.colorLightGreyBlue)),
        Keyframe.ofInt(FIFTH_FRACTION, ContextCompat.getColor(context, R.color.colorGrey)),
        Keyframe.ofInt(LAST_FRACTION, ContextCompat.getColor(context, R.color.colorMiddleGrey))
    )

    val rightBottomBlockAnimator = ObjectAnimator.ofPropertyValuesHolder(loadingView, pvhRightBottomBlockColor)
        .apply { setLoadingAnimator(this, true) }

    val pvhLeftBottomBlockColor = PropertyValuesHolder.ofKeyframe(
        "leftBottomBlockPaint",
        Keyframe.ofInt(FIRST_FRACTION, ContextCompat.getColor(context, R.color.colorGrey)),
        Keyframe.ofInt(SECOND_FRACTION, ContextCompat.getColor(context, R.color.colorMiddleGrey)),
        Keyframe.ofInt(THIRD_FRACTION, ContextCompat.getColor(context, R.color.colorDarkGrey)),
        Keyframe.ofInt(FIFTH_FRACTION, ContextCompat.getColor(context, R.color.colorLightGreyBlue)),
        Keyframe.ofInt(LAST_FRACTION, ContextCompat.getColor(context, R.color.colorGrey))
    )

    val leftBottomBlockAnimator = ObjectAnimator.ofPropertyValuesHolder(loadingView, pvhLeftBottomBlockColor)
        .apply { setLoadingAnimator(this, true) }

    val pvhPulsation = PropertyValuesHolder.ofKeyframe(
        "blockPulsation",
        Keyframe.ofInt(FIRST_FRACTION, 0),
        Keyframe.ofInt(FOURTH_FRACTION, PULSATION),
        Keyframe.ofInt(LAST_FRACTION, 0),
    )

    val pulsationAnimator = ObjectAnimator.ofPropertyValuesHolder(loadingView, pvhPulsation)
        .apply { setLoadingAnimator(this, false) }

    AnimatorSet().apply {
        play(leftTopBlockAnimator).with(rightTopBlockAnimator)
        play(leftTopBlockAnimator).with(rightBottomBlockAnimator)
        play(leftTopBlockAnimator).with(leftBottomBlockAnimator)
        play(leftTopBlockAnimator).with(pulsationAnimator)
        start()
    }
}

fun setLoadingAnimator(animator: ObjectAnimator, isDiscreteEvaluatorShouldSet: Boolean) {
    animator.duration = ANIMATION_DURATION
    animator.repeatCount = Animation.INFINITE
    animator.repeatMode = ObjectAnimator.RESTART

    if (isDiscreteEvaluatorShouldSet) animator.setEvaluator(discreteEvaluator)
}
