package com.cyberwalker.fashionstore.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.login.common.TitleText
import com.cyberwalker.fashionstore.login.state.LoginUiEvent
import com.cyberwalker.fashionstore.navigation.Screen
import com.cyberwalker.fashionstore.splash.SplashScreenActions
import com.cyberwalker.fashionstore.ui.theme.AppTheme

//@Composable
//fun LoginScreen(
//    loginViewModel: LoginViewModel = hiltViewModel(),
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
//    onAction: (actions: LoginScreenActions) -> Unit,
//    navController: NavHostController
//){
//    Scaffold(
//        scaffoldState = scaffoldState,
//        bottomBar = {
//            BottomNav(navController = navController)
//        }
//    ) { innerPadding ->
//        LoginScreenContent(modifier = Modifier.padding(innerPadding), onAction = onAction)
//    }
//}

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onAction: (actions: SplashScreenActions) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController
//    onNavigateToRegistration: () -> Unit,
//    onNavigateToForgotPassword: () -> Unit,
 //   onNavigateToAuthenticatedRoute: () -> Unit
) {

    val loginState by remember { loginViewModel.loginState }

    if (loginState.isLoginSuccessful) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        navController.navigate(Screen.Home.route)
//        LaunchedEffect(key1 = true) {
//            onNavigateToAuthenticatedRoute.invoke()
//        }
    } else {
        // Full Screen Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Main card Content for Login
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.paddingLarge)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(bottom = AppTheme.dimens.paddingExtraLarge)
                ) {

                    // Heading Login
                    TitleText(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
                        text = "Login"//stringResource(id = R.string.login_heading_text)
                    )

                    // Login Inputs Composable
                    LoginInputs(
                        loginState = loginState,
                        onEmailOrMobileChange = { inputString ->
                            loginViewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.EmailOrMobileChanged(
                                    inputString
                                )
                            )
                        },
                        onPasswordChange = { inputString ->
                            loginViewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.PasswordChanged(
                                    inputString
                                )
                            )
                        },
                        onSubmit = {
                            loginViewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)
                        },
                        //onForgotPasswordClick = onNavigateToForgotPassword
                    )

                }
            }

            // Register Section
            Row(
                modifier = androidx.compose.ui.Modifier.padding(AppTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Don't have an account?
                Text(text = "Don't have account?"//stringResource(id = R.string.do_not_have_account)
                 )
                //Register
                Text(
                    modifier = Modifier
                        .padding(start = AppTheme.dimens.paddingExtraSmall)
                        .clickable {
                            navController.navigate(Screen.Signup.route)//onNavigateToRegistration.invoke()
                        },
                    text = "Register",//stringResource(id = R.string.register),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }

}
sealed class LoginScreenActions {
    object Home : LoginScreenActions()
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//        LoginScreen(
//            onNavigateToForgotPassword = {},
//            onNavigateToRegistration = {},
//            onNavigateToAuthenticatedRoute = {}
//        )
//}