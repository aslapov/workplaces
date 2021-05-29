package com.workplaces.aslapov.app.base.viewmodel

import androidx.navigation.NavDirections
import com.redmadrobot.extensions.lifecycle.Event

data class MessageEvent(val message: Int) : Event

sealed class NavigationEvent : Event
data class Navigate(val direction: NavDirections) : NavigationEvent()
object NavigateUp : NavigationEvent()
