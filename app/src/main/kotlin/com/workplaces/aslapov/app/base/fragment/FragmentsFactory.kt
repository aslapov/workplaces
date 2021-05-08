package com.workplaces.aslapov.app.base.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class FragmentsFactory @Inject constructor(
    private val providers: Map<Class<out Fragment>,
        @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = classLoader.loadClass(className)
        return providers[fragmentClass]?.get() ?: super.instantiate(classLoader, className)
    }
}
