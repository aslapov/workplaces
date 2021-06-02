package com.workplaces.aslapov

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val BLOCK_WIDTH_DIVIDER = 9f
        private const val INDENT_DIVIDER = BLOCK_WIDTH_DIVIDER * 10
        private const val BLOCK_RADIUS_DIVIDER = 40 / 14f
    }

    var leftTopBlockColor: Int = ContextCompat.getColor(context, R.color.colorLightGreyBlue)
        set(value) {
            field = value
            invalidate()
        }

    var rightTopBlockColor: Int = ContextCompat.getColor(context, R.color.colorGrey)
        set(value) {
            field = value
            invalidate()
        }

    var rightBottomBlockColor: Int = ContextCompat.getColor(context, R.color.colorMiddleGrey)
        set(value) {
            field = value
            invalidate()
        }

    var leftBottomBlockColor: Int = ContextCompat.getColor(context, R.color.colorDarkGrey)
        set(value) {
            field = value
            invalidate()
        }

    var blockPulsation: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2
        val centerY = height / 2

        val blockWidth = width / BLOCK_WIDTH_DIVIDER
        val indent = width / INDENT_DIVIDER
        val cornerRadius = blockWidth / BLOCK_RADIUS_DIVIDER

        val leftTopBlockRect = RectF(
            centerX - blockPulsation - indent - blockWidth,
            centerY - blockPulsation - indent - blockWidth,
            centerX - blockPulsation - indent,
            centerY - blockPulsation - indent
        )

        val rightTopBlockRect = RectF(
            centerX + blockPulsation + indent,
            centerY - blockPulsation - indent - blockWidth,
            centerX + blockPulsation + indent + blockWidth,
            centerY - blockPulsation - indent
        )

        val rightBottomBlockRect = RectF(
            centerX + blockPulsation + indent,
            centerY + blockPulsation + indent,
            centerX + blockPulsation + indent + blockWidth,
            centerY + blockPulsation + indent + blockWidth
        )

        val leftBottomBlockRect = RectF(
            centerX - blockPulsation - indent - blockWidth,
            centerY + blockPulsation + indent,
            centerX - blockPulsation - indent,
            centerY + blockPulsation + indent + blockWidth
        )

        canvas.apply {
            drawRoundRect(
                leftTopBlockRect,
                cornerRadius,
                cornerRadius,
                paint.apply { color = leftTopBlockColor }
            )

            drawRoundRect(
                rightTopBlockRect,
                cornerRadius,
                cornerRadius,
                paint.apply { color = rightTopBlockColor }
            )

            drawRoundRect(
                rightBottomBlockRect,
                cornerRadius,
                cornerRadius,
                paint.apply { color = rightBottomBlockColor }
            )

            drawRoundRect(
                leftBottomBlockRect,
                cornerRadius,
                cornerRadius,
                paint.apply { color = leftBottomBlockColor }
            )
        }
    }
}
