package com.workplaces.aslapov.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = providers[modelClass]
            ?: providers.asIterable().find { modelClass.isAssignableFrom(it.key) }?.value
            ?: error("Unknown ViewModel class $modelClass")

        return try {
            provider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
