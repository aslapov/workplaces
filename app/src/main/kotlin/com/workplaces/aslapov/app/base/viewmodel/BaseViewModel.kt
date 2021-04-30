package com.workplaces.aslapov.app.base.viewmodel

import androidx.lifecycle.ViewModel
import com.redmadrobot.extensions.lifecycle.EventQueue

open class BaseViewModel : ViewModel() {

    /**
     * LiveData для событий, которые должны быть обработаны один раз
     * Например: показы диалогов, снэкбаров с ошибками
     */
    val eventsQueue = EventQueue()
}
