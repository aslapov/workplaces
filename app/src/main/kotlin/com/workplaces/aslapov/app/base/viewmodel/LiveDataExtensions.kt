package com.workplaces.aslapov.app.base.viewmodel

import androidx.lifecycle.MutableLiveData
import com.redmadrobot.extensions.lifecycle.requireValue
import com.workplaces.aslapov.utils.extension.onNext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> MutableLiveData<T>.delegate(): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            onNext(value)
        }
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return requireValue()
        }
    }
}
