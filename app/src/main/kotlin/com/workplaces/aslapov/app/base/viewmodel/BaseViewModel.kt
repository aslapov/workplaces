package com.workplaces.aslapov.app.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.redmadrobot.extensions.lifecycle.EventQueue

open class BaseViewModel<T : Any> : ViewModel() {

    val eventsQueue = EventQueue()

    val viewState: MutableLiveData<T> = MutableLiveData()
    protected var state: T by viewState.delegate()

    fun navigateTo(direction: NavDirections) {
        eventsQueue.offerEvent(Navigate(direction))
    }

    fun navigateUp() {
        eventsQueue.offerEvent(NavigateUp)
    }
}
