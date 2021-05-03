package com.workplaces.aslapov.app

import com.redmadrobot.extensions.lifecycle.Event

data class MessageEvent(val message: Int) : Event
data class ErrorMessageEvent(val errorMessage: Int) : Event
