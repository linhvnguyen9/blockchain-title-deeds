package com.linh.titledeed.presentation

import com.linh.titledeed.NavigationCommand
import com.linh.titledeed.NavigationDirections
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {
    val commands = MutableStateFlow(NavigationDirections.default)

    fun navigate(directions: NavigationCommand) {
        commands.value = directions
    }
}