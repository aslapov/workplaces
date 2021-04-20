package com.redmadrobot.app.ui.base.viewmodel

import androidx.navigation.NavDirections

interface Event

sealed class NavigationEvent : Event

data class Navigate(val direction: NavDirections) : NavigationEvent()

object NavigateUp : NavigationEvent()
