package com.workplaces.aslapov

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

const val BLOCK_WIDTH_DIVIDER = 9f
const val INDENT_DIVIDER = 90f
const val BLOCK_RADIUS_DIVIDER = 2.857f

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var leftTopBlockColor: Int = ContextCompat.getColor(context, R.color.colorLightGreyBlue)
        set(value) {
            field = value
            invalidate()
        }

    var rightTopBlockPaint: Int = ContextCompat.getColor(context, R.color.colorGrey)
        set(value) {
            field = value
            invalidate()
        }

    var rightBottomBlockPaint: Int = ContextCompat.getColor(context, R.color.colorMiddleGrey)
        set(value) {
            field = value
            invalidate()
        }

    var leftBottomBlockPaint: Int = ContextCompat.getColor(context, R.color.colorDarkGrey)
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
                paint.apply { color = rightTopBlockPaint }
            )

            drawRoundRect(
                rightBottomBlockRect,
                cornerRadius,
                cornerRadius,
                paint.apply { color = rightBottomBlockPaint }
            )

            drawRoundRect(
                leftBottomBlockRect,
                cornerRadius,
                cornerRadius,
                paint.apply { color = leftBottomBlockPaint }
            )
        }
    }
}
