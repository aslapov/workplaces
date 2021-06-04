package com.workplaces.aslapov

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val ANIMATION_DURATION: Long = 600
        private const val BLOCKS_COUNT = 4
        private const val BLOCK_WIDTH_DIVIDER = 9f
        private const val INDENT_DIVIDER = 10
        private const val BLOCK_RADIUS_DIVIDER = 40 / 14f
        private const val PULSATION_TICK_COUNT = 10f
        private const val COLOR_ANIMATION_TICK_COUNT = 4
    }

    private val loadingColors: List<Int> = listOf(
        ContextCompat.getColor(context, R.color.colorLightGreyBlue),
        ContextCompat.getColor(context, R.color.colorGrey),
        ContextCompat.getColor(context, R.color.colorMiddleGrey),
        ContextCompat.getColor(context, R.color.colorDarkGrey),
    )

    private val loadingBlocks: List<Square> = listOf(Square(), Square(), Square(), Square())

    private var blockPulsation: Float = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val centerX = w / 2
        val centerY = h / 2
        val blockSize = w / BLOCK_WIDTH_DIVIDER
        val indent = blockSize / INDENT_DIVIDER
        val offset = blockSize + indent

        for (i in 0 until BLOCKS_COUNT) {
            loadingBlocks[i].setSize(blockSize)
            loadingBlocks[i].setColor(loadingColors[i])
        }

        loadingBlocks[0].move(centerX - offset, centerY - offset)
        loadingBlocks[1].move(centerX + indent, centerY - offset)
        loadingBlocks[2].move(centerX + indent, centerY + indent)
        loadingBlocks[3].move(centerX - offset, centerY + indent)
    }

    override fun onDraw(canvas: Canvas) {
        val blockSize = width / BLOCK_WIDTH_DIVIDER
        val cornerRadius = blockSize / BLOCK_RADIUS_DIVIDER

        for (block in loadingBlocks) {
            canvas.drawRoundRect(block.rect, cornerRadius, cornerRadius, block.paint)
        }
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)
        if (isVisible) {
            startAnimation()
        } else {
            clearAnimation()
        }
    }

    private fun startAnimation() {
        val pulsationAnimator = ValueAnimator.ofFloat(0f, PULSATION_TICK_COUNT).apply {
            duration = ANIMATION_DURATION / 2
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.REVERSE

            addUpdateListener {
                val centerX = width / 2
                val centerY = height / 2
                val blockSize = width / BLOCK_WIDTH_DIVIDER
                val indent = blockSize / INDENT_DIVIDER
                val offset = blockSize + indent
                blockPulsation = it.animatedValue as Float

                loadingBlocks[0].moveTo(centerX - offset - blockPulsation, centerY - offset - blockPulsation)
                loadingBlocks[1].moveTo(centerX + indent + blockPulsation, centerY - offset - blockPulsation)
                loadingBlocks[2].moveTo(centerX + indent + blockPulsation, centerY + indent + blockPulsation)
                loadingBlocks[3].moveTo(centerX - offset - blockPulsation, centerY + indent + blockPulsation)
                invalidate()
            }
        }

        val colorAnimation = ValueAnimator.ofInt(0, COLOR_ANIMATION_TICK_COUNT).apply {
            duration = ANIMATION_DURATION
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()

            addUpdateListener {
                val animatedValue = it.animatedValue as Int
                for (i in 0 until BLOCKS_COUNT) {
                    val index = (i + animatedValue) % BLOCKS_COUNT
                    loadingBlocks[i].setColor(loadingColors[index])
                }
                invalidate()
            }
        }

        AnimatorSet().apply {
            play(colorAnimation).with(pulsationAnimator)
            start()
        }
    }

    private class Square {
        val rect = RectF()

        val paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        fun setSize(size: Float) {
            rect.set(0f, 0f, size, size)
        }

        fun move(dx: Float, dy: Float) {
            rect.offset(dx, dy)
        }

        fun moveTo(left: Float, top: Float) {
            rect.offsetTo(left, top)
        }

        fun setColor(color: Int) {
            paint.color = color
        }
    }
}
