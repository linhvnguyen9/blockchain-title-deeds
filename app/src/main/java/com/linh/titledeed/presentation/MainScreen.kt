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
import com.linh.titledeed.domain.entity.TransactionType
import com.linh.titledeed.presentation.deeds.*
import com.linh.titledeed.presentation.home.HomeScreen
import com.linh.titledeed.presentation.home.HomeViewModel
import com.linh.titledeed.presentation.transaction.TransactionInfoDialog
import com.linh.titledeed.presentation.transaction.TransactionInfoViewModel
import com.linh.titledeed.presentation.utils.convertToBalanceString
import com.linh.titledeed.presentation.utils.getErrorStringResource
import com.linh.titledeed.presentation.utils.parentViewModel
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
                    val sales = homeViewModel.sales.collectAsState()

                    Timber.d("MainScreen sales $sales")

                    HomeScreen(
                        sales.value,
                        onClickSale = {
                            homeViewModel.onClickSale(it)
                        }
                    )
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
                    val ownedDeedsViewModel: OwnedDeedsViewModel = parentViewModel(navController, NavigationDirections.ownedDeeds.destination)

                    val deeds = ownedDeedsViewModel.deeds.collectAsState()
                    val isRefreshing = ownedDeedsViewModel.isRefreshing.collectAsState()

                    OwnedDeedsScreen(
                        deeds.value,
                        isRefreshing = isRefreshing.value,
                        onClickDeed = { ownedDeedsViewModel.onClickDeed(it) },
                        onRefresh = { ownedDeedsViewModel.onRefresh() })
                }
                composable(
                    NavigationDirections.DeedDetailNavigation.route,
                    NavigationDirections.DeedDetailNavigation.args
                ) {
                    val token =
                        it.arguments?.getString(NavigationDirections.DeedDetailNavigation.KEY_TOKEN_ID)
                            ?: ""

                    val deedDetailViewModel: DeedDetailViewModel = parentViewModel(navController, NavigationDirections.DeedDetailNavigation.route)

                    val deed = deedDetailViewModel.deed.collectAsState()
                    val sale = deedDetailViewModel.sale.collectAsState()
                    val isOwner = deedDetailViewModel.isOwner.collectAsState()
                    val isRefreshing = deedDetailViewModel.isRefreshing.collectAsState()

                    LaunchedEffect(key1 = token) {
                        deedDetailViewModel.getDeed(token)
                    }

                    DeedDetailScreen(
                        deed.value,
                        sale.value,
                        isOwner.value,
                        isRefreshing.value,
                        onClickSell = {
                            deedDetailViewModel.onClickSell()
                        },
                        onClickCancelSell = {
                            deedDetailViewModel.onClickCancelSell()
                        },
                        onClickTransferOwnership = {
                            deedDetailViewModel.onClickTransfer()
                        },
                        onRefresh = {
                            deedDetailViewModel.getDeed(token)
                        }
                    )
                }
                composable(
                    NavigationDirections.TransferOwnershipNavigation.route,
                    NavigationDirections.TransferOwnershipNavigation.args
                ) {
                    val viewModel: TransferDeedOwnershipViewModel = hiltViewModel()

                    val tokenId =
                        it.arguments?.getString(NavigationDirections.TransferOwnershipNavigation.KEY_TOKEN_ID)
                            ?: ""
                    val receiverAddress = viewModel.receiverAddress.collectAsState()
                    val receiverAddressError = viewModel.receiverAddressError.collectAsState()

                    LaunchedEffect(key1 = tokenId, key2 = receiverAddress) {
                        viewModel.setTokenId(tokenId)
                    }

                    TransferDeedOwnershipScreen(
                        receiverAddress.value,
                        getErrorStringResource(receiverAddressError.value),
                        onReceiverAddressChange = { viewModel.setReceiverAddress(it) },
                        onClickSubmit = { viewModel.onClickSubmit() }
                    )
                }
                composable(
                    NavigationDirections.SellDeedNavigation.route,
                    NavigationDirections.SellDeedNavigation.args
                ) { navBackStackEntry ->
                    val viewModel: SellDeedViewModel = hiltViewModel()

                    val tokenId =
                        navBackStackEntry.arguments?.getString(NavigationDirections.SellDeedNavigation.KEY_TOKEN_ID)
                            ?: ""

                    LaunchedEffect(key1 = tokenId) {
                        viewModel.setTokenId(tokenId)
                    }

                    val title = viewModel.saleTitle.collectAsState()
                    val titleError = viewModel.saleTitleError.collectAsState()
                    val description = viewModel.saleDescription.collectAsState()
                    val phoneNumber = viewModel.phoneNumber.collectAsState()
                    val phoneNumberError = viewModel.phoneNumberError.collectAsState()
                    val salePriceInWei = viewModel.priceInWei.collectAsState()
                    val salePriceInWeiError = viewModel.priceInWeiError.collectAsState()
                    val uploadMetadataResponse = viewModel.uploadMetadataResponse.collectAsState()

                    SellDeedScreen(
                        title.value,
                        getErrorStringResource(titleError.value),
                        onTitleChange = { viewModel.setSaleTitle(it) },
                        description.value,
                        onDescriptionChange = { viewModel.setSaleDescription(it) },
                        phoneNumber.value,
                        getErrorStringResource(phoneNumberError.value),
                        onPhoneNumberChange = { viewModel.setPhoneNumber(it) },
                        salePriceInWei.value,
                        getErrorStringResource(salePriceInWeiError.value),
                        onSalePriceChange = { viewModel.setPriceInWei(it) },
                        onClickSubmit = { viewModel.onClickSubmit() },
                        uploadMetadataResponse = uploadMetadataResponse.value
                    )
                }
                dialog(
                    NavigationDirections.TransactionInfoNavigation.route,
                    NavigationDirections.TransactionInfoNavigation.args,
                ) {
                    val ownedDeedsViewModel: OwnedDeedsViewModel =
                        parentViewModel(navController, NavigationDirections.ownedDeeds.destination)
                    val deedDetailViewModel: DeedDetailViewModel =
                        parentViewModel(navController, NavigationDirections.DeedDetailNavigation.route)

                    val transactionInfoViewModel: TransactionInfoViewModel = hiltViewModel()

                    val transactionTypeString =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_TRANSACTION_TYPE)
                            ?: ""
                    val transactionType = TransactionType.valueOf(transactionTypeString)
                    val receiverAddress =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_RECEIVER_ADDRESS)
                            ?: ""
                    val tokenId =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_TOKEN_ID)
                            ?: ""
                    val uri =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_URI)
                            ?: ""
                    val price =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_PRICE)
                            ?: ""
                    val navigateBackDestination =
                        it.arguments?.getString(NavigationDirections.TransactionInfoNavigation.KEY_NAVIGATE_BACK_DESTINATION)
                            ?: ""

                    Timber.d("Navigate to dialog transactionType $transactionType receiverAddress $receiverAddress tokenId $tokenId")

                    val transaction = transactionInfoViewModel.transaction.collectAsState()
                    val transactionResponse = transactionInfoViewModel.transactionResponse.collectAsState()

                    LaunchedEffect(key1 = transactionType, key2 = receiverAddress) {
                        transactionInfoViewModel.getTransactionDetail(
                            transactionType,
                            receiverAddress,
                            tokenId,
                            price,
                            uri,
                            navigateBackDestination
                        )
                    }

                    TransactionInfoDialog(
                        transaction.value,
                        transactionResponse.value,
                        onConfirm = { transactionInfoViewModel.onConfirm() },
                        onDismiss = {
                            deedDetailViewModel.getDeed(tokenId)
                            ownedDeedsViewModel.onRefresh()
                            transactionInfoViewModel.onDismiss(it)
                        }
                    )
                }
            }
        }

        navigationManager.commands.collectAsState().value.also { command ->
            if (command?.direction == NavigationDirections.back) {
                if (command.popUpTo?.destination != null) {
                    navController.popBackStack(command.popUpTo.destination, command.inclusive)
                } else {
                    navController.popBackStack()
                }
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