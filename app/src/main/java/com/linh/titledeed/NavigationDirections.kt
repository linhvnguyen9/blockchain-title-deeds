package com.linh.titledeed

import androidx.navigation.NamedNavArgument
import timber.log.Timber

object NavigationDirections {
    val welcome = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "welcome"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_welcome

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val onboardWallet = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "onboard_wallet"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val createWallet = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "create_wallet"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val enterWalletPassword = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "enter_wallet_password"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val inputWallet = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "input_wallet"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val walletMnemonic = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "wallet_mnemonic"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val confirmMnemonic = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "confirm_mnemonic"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet

        override fun equals(other: Any?): Boolean {
            return false
        }
    }

    val main = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "main"

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet
    }

    val home = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "home"

        override val isBottomNavigationItem: Boolean = true

        override val screenNameRes: Int
            get() = R.string.all_home
    }

    val wallet = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "wallet"

        override val isBottomNavigationItem: Boolean = true

        override val screenNameRes: Int
            get() = R.string.all_wallet
    }

    val ownedDeeds = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "owned_deeds"

        override val isBottomNavigationItem: Boolean = true

        override val screenNameRes: Int
            get() = R.string.all_wallet
    }

    //Special direction for navigate back inclusive
    val current = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = ""

        override val isBottomNavigationItem: Boolean = false

        override val screenNameRes: Int
            get() = R.string.all_wallet
    }

    val default = welcome
}

@Suppress("EqualsOrHashCode")
abstract class NavigationDirection {
    abstract val arguments: List<NamedNavArgument>
    abstract val destination: String
    abstract val isBottomNavigationItem: Boolean
    abstract val screenNameRes: Int

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }
}