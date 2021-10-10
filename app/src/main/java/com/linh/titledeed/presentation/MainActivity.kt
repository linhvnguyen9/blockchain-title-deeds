package com.linh.titledeed.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.linh.titledeed.NavigationDirections
import com.linh.titledeed.presentation.onboard.wallet.*
import com.linh.titledeed.presentation.onboard.wallet.createwallet.ConfirmMnemonicScreen
import com.linh.titledeed.presentation.onboard.wallet.createwallet.CreateWalletScreen
import com.linh.titledeed.presentation.onboard.wallet.createwallet.CreateWalletViewModel
import com.linh.titledeed.presentation.onboard.wallet.createwallet.MnemonicScreen
import com.linh.titledeed.presentation.onboard.welcome.WelcomeScreen
import com.linh.titledeed.presentation.ui.theme.BlockchainTitleDeedsTheme
import com.linh.titledeed.presentation.onboard.welcome.WelcomeScreenViewModel
import com.linh.titledeed.presentation.utils.parentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                    composable(NavigationDirections.wallet.destination) {
                        val walletViewModel: WalletViewModel = hiltViewModel()
                        WalletScreen(
                            onClickCreate = { walletViewModel.onClickCreateWallet() },
                            onClickInput = { walletViewModel.onClickInputWallet() }
                        )
                    }
                    navigation(startDestination = NavigationDirections.enterWalletPassword.destination, NavigationDirections.createWallet.destination) {
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

                            val mnemonicWords = createWalletViewModel.mnemonic

                            ConfirmMnemonicScreen(mnemonicWords.value) {
                                createWalletViewModel.onConfirmMnemonic()
                            }
                        }
                    }
                    composable(NavigationDirections.inputWallet.destination) {
                        val inputWalletViewModel: InputWalletViewModel = hiltViewModel()

                        val mnemonic = inputWalletViewModel.mnemonic.collectAsState()
                        val password = inputWalletViewModel.password.collectAsState()

                        InputWalletScreen(
                            password.value,
                            onPasswordChange = { inputWalletViewModel.onPasswordChange(it) },
                            mnemonic.value,
                            onMnemonicChange = { inputWalletViewModel.onMnemonicChange(it) }
                        ) {
                            inputWalletViewModel.onClickSubmit()
                        }
                    }
                    composable(NavigationDirections.home.destination) {
                        HomeScreen()
                    }
                }
            }

            navigationManager.commands.collectAsState().value.also { command ->
                if (command.destination.isNotEmpty() && !command.isBottomNavigationItem) {
                    navController.navigate(command.destination)
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