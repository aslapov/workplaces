package com.redmadrobot.app.utils.extension

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Исправление проблемы с fitsSystemWindow.
 * По умолчанию информация об insets не передаётся дочерним view,
 * поэтому происходят ошибки при использование fitsSystemWindow с fragments.
 * Этот метод передаёт информацию об insets в дочернии view.
 *
 * Подробности тут: https://medium.com/androiddevelopers/windows-insets-fragment-transitions-9024b239a436
 */
fun View.dispatchApplyWindowInsetsToChild() {
    setOnApplyWindowInsetsListener { view, insets ->
        var consumed = false

        (view as? ViewGroup)?.forEach { child ->
            val childResult = child.dispatchApplyWindowInsets(insets)
            if (childResult.isConsumed) consumed = true
        }

        // If any of the children consumed the insets, return
        // an appropriate value
        if (consumed) insets.consumeSystemWindowInsets() else insets
    }
}
