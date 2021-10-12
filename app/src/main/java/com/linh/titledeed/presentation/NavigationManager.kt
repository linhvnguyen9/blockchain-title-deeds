package com.linh.titledeed.presentation

import com.linh.titledeed.NavigationDirection
import com.linh.titledeed.NavigationDirections
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {
    val commands = MutableStateFlow<NavigationCommand?>(null)

    fun navigate(command: NavigationCommand) {
        commands.value = command
    }

    fun navigate(direction: NavigationDirection) {
        commands.value = NavigationCommand(direction)
    }
}

data class NavigationCommand(val direction: NavigationDirection, val popUpTo: NavigationDirection? = null, val inclusive: Boolean = false)