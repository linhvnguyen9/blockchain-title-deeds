package com.linh.titledeed.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.R
import com.linh.titledeed.domain.entity.Transaction
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.presentation.deeds.DeedDetailScreen
import com.linh.titledeed.presentation.deeds.DeedDetailViewModel
import com.linh.titledeed.presentation.deeds.TransferDeedOwnershipScreen
import com.linh.titledeed.presentation.deeds.TransferDeedOwnershipViewModel
import com.linh.titledeed.presentation.home.HomeViewModel
import com.linh.titledeed.presentation.transaction.TransactionInfoDialog
import com.linh.titledeed.presentation.transaction.TransactionInfoViewModel
import com.linh.titledeed.presentation.utils.convertToBalanceString
import com.linh.titledeed.presentation.wallet.OwnedDeedsScreen
import com.linh.titledeed.presentation.wallet.OwnedDeedsViewModel
import com.linh.titledeed.presentation.wallet.WalletScreen
import com.linh.titledeed.presentation.wallet.WalletViewModel
import timber.log.Timber

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    navigationManager: NavigationManager,
    onNavigateHome: () -> Unit,
    onNavigateWallet: () -> Unit,
) {
    val navController = rememberNavController()

    val bottomNavigationItems =
        listOf(NavigationDirections.home, NavigationDirections.wallet)

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavigationItems.forEach { screen ->
                    Timber.d("Bottom navigation item $screen")
                    BottomNavigationItem(
                        icon = {
                            val icon = when (screen) {
                                NavigationDirections.home -> painterResource(R.drawable.ic_baseline_home_24)
                                NavigationDirections.wallet -> painterResource(R.drawable.ic_baseline_account_balance_wallet_24)
                                else -> painterResource(R.drawable.ic_launcher_foreground)
                            }
                            Icon(icon, null)
                        },
                        label = { Text(stringResource(screen.screenNameRes)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.destination } == true,
                        onClick = {
                            when (screen) {
                                NavigationDirections.home -> onNavigateHome()
                                NavigationDirections.wallet -> onNavigateWallet()
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            NavHost(
                navController,
                startDestination = NavigationDirections.home.destination
            ) {
                composable(NavigationDirections.home.destination) {
                    val homeViewModel: HomeViewModel = hiltViewModel()

                    val wallet = homeViewModel.wallet.collectAsState()

                    HomeScreen(wallet.value)
                }
                composable(NavigationDirections.wallet.destination) {
                    val walletViewModel: WalletViewModel = hiltViewModel()

                    val wallet = walletViewModel.wallet.collectAsState().value
                    val ethBalance = wallet.balance.convertToBalanceString()

                    WalletScreen(
                        wallet,
                        ethBalance,
                        onClickLogout = { walletViewModel.onClickLogout() },
                        onClickViewOwnedDeeds = { walletViewModel.onClickViewOwnedDeeds() }
                    )
                }
                composable(NavigationDirections.ownedDeeds.destination) {
                    val ownedDeedsViewModel: OwnedDeedsViewModel = hiltViewModel()

                    val deeds = ownedDeedsViewModel.deeds.collectAsState()

                    OwnedDeedsScreen(deeds.value, onClickDeed = {ownedDeedsViewModel.onClickDeed(it)})
                }
                composable(
                    NavigationDirections.DeedDetailNavigation.route,
                    NavigationDirections.DeedDetailNavigation.args
                ) {
                    val deedDetailViewModel: DeedDetailViewModel = hiltViewModel()

                    val token =
                        it.arguments?.getString(NavigationDirections.DeedDetailNavigation.KEY_TOKEN_ID)
                            ?: ""
                    val deed = deedDetailViewModel.deed.collectAsState()

                    LaunchedEffect(key1 = token) {
                        deedDetailViewModel.getDeed(token)
                    }

                    DeedDetailScreen(deed.value) {
                        deedDetailViewModel.onClickTransfer()
                    }
                }
                composable(NavigationDirections.transferOwnership.destination) {
                    val viewModel: TransferDeedOwnershipViewModel = hiltViewModel()

                    val receiverAddress = viewModel.receiverAddress.collectAsState()

                    TransferDeedOwnershipScreen(
                        receiverAddress.value,
                        onReceiverAddressChange = { viewModel.setReceiverAddress(it) },
                        onClickSubmit = { viewModel.onClickSubmit() }
                    )
                }
                dialog(
                    NavigationDirections.TransactionInfoNavigation.route,
                    NavigationDirections.TransactionInfoNavigation.args
                ) {
                    val transactionInfoViewModel: TransactionInfoViewModel = hiltViewModel()

                    val transactionTypeString =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_TRANSACTION_TYPE)
                            ?: ""
                    val transactionType = TransactionType.valueOf(transactionTypeString)
                    val receiverAddress =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_RECEIVER_ADDRESS)
                            ?: ""

                    val transaction = transactionInfoViewModel.transaction.collectAsState()

                    LaunchedEffect(key1 = transactionType, key2 = receiverAddress) {
                        transactionInfoViewModel.getTransactionDetail(transactionType, receiverAddress)
                    }

                    TransactionInfoDialog(
                        transaction.value,
                        onConfirm = { /*TODO*/ },
                        onDismiss = { transactionInfoViewModel.onDismiss() }
                    )
                }
            }
        }

        navigationManager.commands.collectAsState().value.also { command ->
            if (command?.direction == NavigationDirections.back) {
                navController.popBackStack()
            } else {
                command?.let {
                    if (it.direction.destination.isNotEmpty() && it.direction.isBottomNavigationItem) {
                        navController.navigate(it.direction.destination)
                    }
                }
            }
        }
    }
}