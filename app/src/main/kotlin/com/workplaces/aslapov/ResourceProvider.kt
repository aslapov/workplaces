package com.workplaces.aslapov

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import javax.inject.Inject

interface ResourceProvider {
    fun getString(@StringRes res: Int, vararg args: Any): String
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String
}

class ApplicationResourceProvider @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getString(res: Int, vararg args: Any): String {
        return context.getString(res, *args)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(id, quantity, *formatArgs)
    }
}
