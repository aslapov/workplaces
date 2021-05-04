package com.workplaces.aslapov.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

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

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
