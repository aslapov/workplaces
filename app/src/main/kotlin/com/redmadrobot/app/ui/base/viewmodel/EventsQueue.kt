package com.redmadrobot.app.ui.base.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import java.util.*

/**
 * Класс-очередь для обработки временных событий, не являющихся частью View
 * Например, показ SnackBar с сообщением или ошибкой.
 */
class
EventsQueue : MutableLiveData<Queue<Event>>() {

    @MainThread
    fun offer(event: Event) {
        val queue = (value ?: LinkedList()).apply {
            add(event)
        }

        value = queue
    }
}
