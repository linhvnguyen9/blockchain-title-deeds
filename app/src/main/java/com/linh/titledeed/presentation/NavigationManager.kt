package com.linh.titledeed.presentation

import com.linh.titledeed.NavigationDirection
import com.linh.titledeed.NavigationDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigationManager {
    val commands = MutableSharedFlow<NavigationCommand?>()

    suspend fun navigate(command: NavigationCommand) {
        commands.emit(command)
    }

    suspend fun navigate(direction: NavigationDirection) {
        commands.emit(NavigationCommand(direction))
    }
}

data class NavigationCommand(val direction: NavigationDirection, val popUpTo: NavigationDirection? = null, val inclusive: Boolean = false)