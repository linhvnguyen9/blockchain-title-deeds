package com.linh.titledeed

import androidx.navigation.NamedNavArgument

object NavigationDirections {
    val createWallet = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "create_wallet"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet
    }

    val default = createWallet
}

interface NavigationCommand {
    val arguments: List<NamedNavArgument>
    val destination: String
    val isBottomNavigationItem: Boolean
    val screenNameRes: Int
}