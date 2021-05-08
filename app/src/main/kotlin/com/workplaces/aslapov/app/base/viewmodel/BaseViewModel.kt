package com.workplaces.aslapov.app.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.redmadrobot.extensions.lifecycle.EventQueue

open class BaseViewModel : ViewModel() {

    /**
     * LiveData для событий, которые должны быть обработаны один раз
     * Например: показы диалогов, снэкбаров с ошибками
     */
    val eventsQueue = EventQueue()

    fun navigateTo(direction: NavDirections) {
        eventsQueue.offerEvent(Navigate(direction))
    }
}

sealed class State<T>
class Content<T>(val data: T) : State<T>()
class Loading<T> : State<T>()
class Error<T> : State<T>()
