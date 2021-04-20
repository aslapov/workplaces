package com.redmadrobot.app.utils.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.ui.base.viewmodel.Event
import com.redmadrobot.app.ui.base.viewmodel.EventsQueue
import java.util.*

fun <T> MutableLiveData<T>.onNext(next: T) {
    this.value = next
}

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null
): T {
    return if (viewModelFactory != null) {
        ViewModelProvider(this, viewModelFactory)[T::class.java]
    } else {
        ViewModelProvider(this)[T::class.java]
    }
}

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null,
    body: T.() -> Unit
): T {
    val vm = if (viewModelFactory != null) {
        ViewModelProvider(this, viewModelFactory)[T::class.java]
    } else {
        ViewModelProvider(this)[T::class.java]
    }
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null
): T {
    return if (viewModelFactory != null) {
        ViewModelProvider(this, viewModelFactory)[T::class.java]
    } else {
        ViewModelProvider(this)[T::class.java]
    }
}

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null,
    body: T.() -> Unit
): T {
    val vm = if (viewModelFactory != null) {
        ViewModelProvider(this, viewModelFactory)[T::class.java]
    } else {
        ViewModelProvider(this)[T::class.java]
    }
    vm.body()
    return vm
}

inline fun <reified T : Any, reified L : LiveData<T>> Fragment.observe(
    liveData: L,
    noinline block: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer<T> { it?.let { block.invoke(it) } })
}

/**
 * Подписка на live data с очередью одноразовых event
 * Например, показы snackbar, диалогов
 */
fun Fragment.observe(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
    eventsQueue.observe(
        this.viewLifecycleOwner,
        Observer<Queue<Event>> { queue: Queue<Event>? ->
            while (queue != null && queue.isNotEmpty()) {
                eventHandler(queue.poll()!!)
            }
        }
    )
}
