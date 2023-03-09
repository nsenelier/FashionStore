package com.cyberwalker.fashionstore.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.login.common.TitleText
import com.cyberwalker.fashionstore.login.state.LoginUiEvent
import com.cyberwalker.fashionstore.navigation.Screen
import com.cyberwalker.fashionstore.splash.SplashScreenActions
import com.cyberwalker.fashionstore.ui.theme.AppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: SplashScreenActions) -> Unit,
    navController: NavHostController
){
    Scaffold(
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        LoginScreenContent(modifier = Modifier.padding(innerPadding), onAction = onAction,
            navController = navController)
    }
}

@Composable
fun LoginScreenContent(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onAction: (actions: SplashScreenActions) -> Unit,
    modifier: Modifier,
    navController: NavHostController

) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val loginState by remember { loginViewModel.loginState }
    val googleSignInState by remember { loginViewModel.googleState }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                loginViewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                Log.d("Google Launcher", "Error: ${it.message}")
            }
        }


    if (loginState.isLoginSuccessful) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        LaunchedEffect(key1 = true) {
            navController.navigate(Screen.Home.route)
       }
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
                modifier = Modifier.padding(AppTheme.dimens.paddingNormal),
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

            //Google Sign-In
            Text(text = "Sign-in with Google", fontWeight = FontWeight.Medium, color = Color.Black)
            Row(
                modifier = Modifier.padding(AppTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(Constants.ServerClient)
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)

                    launcher.launch(googleSignInClient.signInIntent)


                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            LaunchedEffect(key1 = googleSignInState.success){
                scope.launch {
                    if (googleSignInState.success != null) {
                        Toast.makeText(context, "Google Sign-in Success", Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.Home.route)
                    }
                }
            }
        }

    }

}
sealed class LoginScreenActions {
    object Home : LoginScreenActions()
}

object Constants{
    const val ServerClient = " 582051953786-uftmt06mg48gisu412s6t0ec2afi8m8g.apps.googleusercontent.com "
}

