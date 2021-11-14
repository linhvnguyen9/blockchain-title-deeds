package com.linh.titledeed.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.home.HomeScreen
import com.linh.titledeed.presentation.home.HomeViewModel
import com.linh.titledeed.presentation.onboard.wallet.*
import com.linh.titledeed.presentation.onboard.wallet.createwallet.ConfirmMnemonicScreen
import com.linh.titledeed.presentation.onboard.wallet.createwallet.CreateWalletScreen
import com.linh.titledeed.presentation.onboard.wallet.createwallet.CreateWalletViewModel
import com.linh.titledeed.presentation.onboard.wallet.createwallet.MnemonicScreen
import com.linh.titledeed.presentation.onboard.welcome.WelcomeScreen
import com.linh.titledeed.presentation.ui.theme.BlockchainTitleDeedsTheme
import com.linh.titledeed.presentation.onboard.welcome.WelcomeScreenViewModel
import com.linh.titledeed.presentation.utils.getErrorStringResource
import com.linh.titledeed.presentation.utils.parentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BlockchainTitleDeedsTheme {
                NavHost(
                    navController,
                    NavigationDirections.default.destination
                ) {
                    composable(NavigationDirections.default.destination) {
                        val welcomeScreenViewModel: WelcomeScreenViewModel = hiltViewModel()
                        WelcomeScreen {
                            welcomeScreenViewModel.onClickContinue()
                        }
                    }
                    composable(NavigationDirections.onboardWallet.destination) {
                        val walletViewModel: WalletViewModel = hiltViewModel()
                        WalletScreen(
                            onClickCreate = { walletViewModel.onClickCreateWallet() },
                            onClickInput = { walletViewModel.onClickInputWallet() }
                        )
                    }
                    createWallet(navController)
                    composable(NavigationDirections.inputWallet.destination) {
                        val inputWalletViewModel: RecoverWalletViewModel = hiltViewModel()

                        val password = inputWalletViewModel.password.collectAsState()
                        val passwordError = inputWalletViewModel.passwordError.collectAsState()
                        val mnemonic = inputWalletViewModel.mnemonic.collectAsState()
                        val mnemonicError = inputWalletViewModel.mnemonicError.collectAsState()

                        InputWalletScreen(
                            password.value,
                            getErrorStringResource(passwordError.value),
                            onPasswordChange = { inputWalletViewModel.onPasswordChange(it) },
                            mnemonic.value,
                            getErrorStringResource(mnemonicError.value),
                            onMnemonicChange = { inputWalletViewModel.onMnemonicChange(it) }
                        ) {
                            inputWalletViewModel.onClickSubmit()
                        }
                    }
                    composable(NavigationDirections.main.destination) {
                        val mainViewModel: MainViewModel = hiltViewModel()

                        MainScreen(
                            navigationManager,
                            onNavigateHome = { mainViewModel.onClickBottomNavigationHome() },
                            onNavigateWallet = { mainViewModel.onClickBottomNavigationWallet() }
                        )
                    }
                }
            }

            navigationManager.commands.collectAsState().value.also { command ->
                command?.let {
                    if (command.direction.destination.isNotEmpty() && !command.direction.isBottomNavigationItem) {
                        navController.navigate(command.direction.destination) {
                            if (command.popUpTo != null) {
                                if (command.popUpTo == NavigationDirections.current) {
                                    val currentRoute =
                                        navController.currentBackStackEntry?.destination?.route
                                    currentRoute?.let { route ->
                                        popUpTo(route) {
                                            inclusive = command.inclusive
                                        }
                                    }
                                } else {
                                    popUpTo(command.popUpTo.destination) {
                                        inclusive = command.inclusive
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun NavGraphBuilder.createWallet(navController: NavHostController) {
        navigation(
            startDestination = NavigationDirections.enterWalletPassword.destination,
            NavigationDirections.createWallet.destination
        ) {
            composable(NavigationDirections.enterWalletPassword.destination) {
                val createWalletViewModel: CreateWalletViewModel = parentViewModel(
                    navController = navController,
                    parentRoute = NavigationDirections.createWallet.destination
                )

                val password = createWalletViewModel.password.collectAsState()
                val passwordError = createWalletViewModel.passwordError.collectAsState()

                CreateWalletScreen(
                    password.value,
                    onPasswordChange = { createWalletViewModel.onPasswordChange(it) },
                    if (passwordError.value != 0) stringResource(passwordError.value) else "",
                    true
                ) {
                    createWalletViewModel.onSubmitPassword()
                }
            }
            composable(NavigationDirections.walletMnemonic.destination) {
                val createWalletViewModel: CreateWalletViewModel = parentViewModel(
                    navController = navController,
                    parentRoute = NavigationDirections.createWallet.destination
                )

                val mnemonicWords = createWalletViewModel.mnemonic

                MnemonicScreen(mnemonicWords.value) {
                    createWalletViewModel.onConfirmViewedMnemonic()
                }
            }
            composable(NavigationDirections.confirmMnemonic.destination) {
                val createWalletViewModel: CreateWalletViewModel = parentViewModel(
                    navController = navController,
                    parentRoute = NavigationDirections.createWallet.destination
                )

                val selectedMnemonicWords =
                    createWalletViewModel.confirmMnemonicSelected.collectAsState()
                val remainingMnemonicWords =
                    createWalletViewModel.confirmMnemonicRemaining.collectAsState()
                val mnemonicError =
                    createWalletViewModel.confirmMnemonicError.collectAsState()

                ConfirmMnemonicScreen(
                    remainingMnemonicWords.value,
                    selectedMnemonicWords.value,
                    mnemonicError = getErrorStringResource(mnemonicError.value),
                    onAddWord = {
                        createWalletViewModel.onAddConfirmMnemonicWord(it)
                    },
                    onRemoveWord = {
                        createWalletViewModel.onRemoveConfirmMnemonicWord(
                            it
                        )
                    },
                ) {
                    createWalletViewModel.onConfirmMnemonic()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BlockchainTitleDeedsTheme {
        Greeting("Android")
    }
}