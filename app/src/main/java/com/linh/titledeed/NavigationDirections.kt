package com.linh.titledeed

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.linh.titledeed.domain.entity.TransactionType
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

    object TransferOwnershipNavigation {
        const val KEY_TOKEN_ID = "tokenId"

        const val route = "transfer_ownership/{$KEY_TOKEN_ID}"

        val args: List<NamedNavArgument>
            get() = listOf(
                navArgument(KEY_TOKEN_ID) { type = NavType.StringType },
            )

        fun transferOwnership(tokenId: String) = object : NavigationDirection() {
            override val arguments = args

            override val destination: String
                get() = "transfer_ownership/$tokenId"

            override val isBottomNavigationItem: Boolean = true

            override val screenNameRes: Int
                get() = R.string.all_wallet
        }
    }

    object SellDeedNavigation {
        const val KEY_TOKEN_ID = "tokenId"

        const val route = "sell_deed/{$KEY_TOKEN_ID}"

        val args: List<NamedNavArgument>
            get() = listOf(
                navArgument(KEY_TOKEN_ID) { type = NavType.StringType },
            )

        fun sellDeed(tokenId: String) = object : NavigationDirection() {
            override val arguments = args

            override val destination: String
                get() = "sell_deed/$tokenId"

            override val isBottomNavigationItem: Boolean = true

            override val screenNameRes: Int
                get() = R.string.all_wallet
        }
    }

    object TransactionInfoNavigation {
        const val KEY_TRANSACTION_TYPE = "transactionType"
        const val KEY_RECEIVER_ADDRESS = "receiverAddress"
        const val KEY_PRICE = "price"
        const val KEY_URI = "uri"
        const val KEY_NAVIGATE_BACK_DESTINATION = "navigateBack"
        const val KEY_NAVIGATE_BACK_POP_INCLUSIVE = "popInclusive"
        const val KEY_TOKEN_ID = "tokenId"

        const val route = "transaction_info/{$KEY_TRANSACTION_TYPE}?$KEY_RECEIVER_ADDRESS={$KEY_RECEIVER_ADDRESS}&$KEY_TOKEN_ID={$KEY_TOKEN_ID}&$KEY_NAVIGATE_BACK_DESTINATION={$KEY_NAVIGATE_BACK_DESTINATION}&$KEY_NAVIGATE_BACK_POP_INCLUSIVE={$KEY_NAVIGATE_BACK_POP_INCLUSIVE}&$KEY_PRICE={$KEY_PRICE}&$KEY_URI={$KEY_URI}"

        val args: List<NamedNavArgument>
            get() = listOf(
                navArgument(KEY_TRANSACTION_TYPE) {
                    type = NavType.StringType
                },
                navArgument(KEY_TOKEN_ID) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(KEY_RECEIVER_ADDRESS) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(KEY_NAVIGATE_BACK_DESTINATION) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(KEY_NAVIGATE_BACK_POP_INCLUSIVE) {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument(KEY_PRICE) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(KEY_URI) {
                    type = NavType.StringType
                    nullable = true
                },
            )

        fun transactionInfo(transactionType: TransactionType, receiverAddress: String, tokenId: String = "", price: String = "", uri: String = "", navigateBackDestination: String = "", popInclusive: Boolean) = object : NavigationDirection() {
            override val arguments = args

            override val destination: String
                get(){
                    val value = "transaction_info/$transactionType?$KEY_RECEIVER_ADDRESS=$receiverAddress&$KEY_TOKEN_ID=$tokenId&$KEY_NAVIGATE_BACK_DESTINATION=$navigateBackDestination&$KEY_NAVIGATE_BACK_POP_INCLUSIVE=$popInclusive&$KEY_PRICE=$price&$KEY_URI=$uri"
                    Timber.d("transactionInfo $value")
                    return value
                }

            override val isBottomNavigationItem: Boolean = true

            override val screenNameRes: Int
                get() = R.string.all_wallet
        }
    }


    object DeedDetailNavigation {
        const val KEY_TOKEN_ID = "tokenId"

        const val route = "deed_detail/{$KEY_TOKEN_ID}"

        val args: List<NamedNavArgument>
            get() = listOf(
                navArgument(KEY_TOKEN_ID) { type = NavType.StringType }
            )

        fun detail(tokenId: String) = object : NavigationDirection() {
            override val arguments = args

            override val destination: String
                get() = "deed_detail/$tokenId"

            override val isBottomNavigationItem: Boolean = true

            override val screenNameRes: Int
                get() = R.string.app_name
        }
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

    // For navigating back
    val back = object : NavigationDirection() {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = ""

        override val isBottomNavigationItem: Boolean = true

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