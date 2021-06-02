package com.workplaces.aslapov

import android.animation.*
import android.view.animation.Animation
import androidx.core.content.ContextCompat

class AnimationHelper constructor(private val loadingView: LoadingView) {

    companion object {
        private const val ANIMATION_DURATION: Long = 600
        private const val FIRST_FRACTION = 0f
        private const val SECOND_FRACTION = .2f
        private const val THIRD_FRACTION = .4f
        private const val FOURTH_FRACTION = .6f
        private const val FIFTH_FRACTION = .8f
        private const val LAST_FRACTION = 1f
        private const val PULSATION = 10
    }

    private val leftTopBlockColorsHolder = getColorValuesHolder(
        propertyName = loadingView::leftTopBlockColor.name,
        firstColorResId = R.color.colorLightGreyBlue,
        secondColorResId = R.color.colorGrey,
        thirdColorResId = R.color.colorMiddleGrey,
        fourthColorResId = R.color.colorDarkGrey,
    )

    private val rightTopBlockColorsHolder = getColorValuesHolder(
        propertyName = loadingView::rightTopBlockColor.name,
        firstColorResId = R.color.colorDarkGrey,
        secondColorResId = R.color.colorLightGreyBlue,
        thirdColorResId = R.color.colorGrey,
        fourthColorResId = R.color.colorMiddleGrey,
    )

    private val rightBottomBlockColorsHolder = getColorValuesHolder(
        propertyName = loadingView::rightBottomBlockColor.name,
        firstColorResId = R.color.colorMiddleGrey,
        secondColorResId = R.color.colorDarkGrey,
        thirdColorResId = R.color.colorLightGreyBlue,
        fourthColorResId = R.color.colorGrey,
    )

    private val leftBottomBlockColorsHolder = getColorValuesHolder(
        propertyName = loadingView::leftBottomBlockColor.name,
        firstColorResId = R.color.colorGrey,
        secondColorResId = R.color.colorMiddleGrey,
        thirdColorResId = R.color.colorDarkGrey,
        fourthColorResId = R.color.colorLightGreyBlue,
    )

    private val pulsationValuesHolder = PropertyValuesHolder.ofKeyframe(
        loadingView::blockPulsation.name,
        Keyframe.ofInt(FIRST_FRACTION, 0),
        Keyframe.ofInt(FOURTH_FRACTION, PULSATION),
        Keyframe.ofInt(LAST_FRACTION, 0),
    )

    fun start() {
        val discreteEvaluator = TypeEvaluator<Int> { fraction, startValue, _ ->
            (startValue as Number).toInt() + fraction.toInt()
        }

        AnimatorSet().apply {
            play(getAnimator(pulsationValuesHolder))
                .with(getAnimator(leftTopBlockColorsHolder, discreteEvaluator))
                .with(getAnimator(rightTopBlockColorsHolder, discreteEvaluator))
                .with(getAnimator(rightBottomBlockColorsHolder, discreteEvaluator))
                .with(getAnimator(leftBottomBlockColorsHolder, discreteEvaluator))

            start()
        }
    }

    private fun getColorValuesHolder(
        propertyName: String,
        firstColorResId: Int,
        secondColorResId: Int,
        thirdColorResId: Int,
        fourthColorResId: Int,
    ): PropertyValuesHolder {
        val context = loadingView.context

        return PropertyValuesHolder.ofKeyframe(
            propertyName,
            Keyframe.ofInt(FIRST_FRACTION, ContextCompat.getColor(context, firstColorResId)),
            Keyframe.ofInt(SECOND_FRACTION, ContextCompat.getColor(context, secondColorResId)),
            Keyframe.ofInt(THIRD_FRACTION, ContextCompat.getColor(context, thirdColorResId)),
            Keyframe.ofInt(FIFTH_FRACTION, ContextCompat.getColor(context, fourthColorResId)),
            Keyframe.ofInt(LAST_FRACTION, ContextCompat.getColor(context, firstColorResId))
        )
    }

    private fun getAnimator(
        holder: PropertyValuesHolder,
        evaluator: TypeEvaluator<Int> = IntEvaluator(),
    ): ObjectAnimator {
        return ObjectAnimator.ofPropertyValuesHolder(loadingView, holder)
            .apply {
                duration = ANIMATION_DURATION
                repeatCount = Animation.INFINITE
                repeatMode = ObjectAnimator.RESTART
                setEvaluator(evaluator)
            }
    }
}
