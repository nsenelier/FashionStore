package com.cyberwalker.fashionstore.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.login.common.SmallClickableWithIconAndText
import com.cyberwalker.fashionstore.login.common.TitleText
import com.cyberwalker.fashionstore.navigation.Screen
import com.cyberwalker.fashionstore.signup.state.SignupUiEvent
import com.cyberwalker.fashionstore.ui.theme.AppTheme

//@Composable
//fun SignupScreen(
//    signupViewModel: SignupViewModel = hiltViewModel(),
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
//    onAction: (actions: SignupScreenActions) -> Unit,
//    navController: NavHostController
//){
//    Scaffold(
//        scaffoldState = scaffoldState,
//        bottomBar = {
//            BottomNav(navController = navController)
//        }
//    ) { innerPadding ->
//        SignupScreenContent(modifier = Modifier.padding(innerPadding), onAction = onAction)
//    }
//}

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel(),
    onAction: (actions: SignupScreenActions) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController
    //onNavigateBack: () -> Unit,
    //onNavigateToAuthenticatedRoute: () -> Unit
) {

    val signupState by remember { signupViewModel.signupState }

    if (signupState.isSignupSuccessful) {
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
                .verticalScroll(rememberScrollState())
        ) {

            // Back Button Icon
            SmallClickableWithIconAndText(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.paddingLarge)
                    .padding(top = AppTheme.dimens.paddingLarge),
                iconContentDescription = "Navigate to the previous screen",//stringResource(id = R.string.navigate_back),
                iconVector = Icons.Outlined.ArrowBack,
                text = "Back to Login",//stringResource(id = R.string.back_to_login),
                onClick = { navController.navigate(Screen.Login.route) }
            )

            // Main card Content for signup
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
                    // Heading signup
                    TitleText(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
                        text = "Sign Up", //stringResource(id = R.string.signup_heading_text)
                    )

                    SignupInputs(
                        signupState = signupState,
                        onEmailIdChange = { inputString ->
                            signupViewModel.onUiEvent(
                                signupUiEvent = SignupUiEvent.EmailChanged(
                                    inputValue = inputString
                                )
                            )
                        },
                        onMobileNumberChange = { inputString ->
                            signupViewModel.onUiEvent(
                                signupUiEvent = SignupUiEvent.MobileNumberChanged(
                                    inputValue = inputString
                                )
                            )
                        },
                        onPasswordChange = { inputString ->
                            signupViewModel.onUiEvent(
                                signupUiEvent = SignupUiEvent.PasswordChanged(
                                    inputValue = inputString
                                )
                            )
                        },
                        onConfirmPasswordChange = { inputString ->
                            signupViewModel.onUiEvent(
                                signupUiEvent = SignupUiEvent.ConfirmPasswordChanged(
                                    inputValue = inputString
                                )
                            )
                        },
                        onSubmit = {
                            signupViewModel.onUiEvent(signupUiEvent = SignupUiEvent.Submit)
                        }
                    )
                }
            }
        }
    }

}
sealed class SignupScreenActions {
    object Login : SignupScreenActions()
}