package com.cyberwalker.fashionstore.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.login.state.*
import com.cyberwalker.fashionstore.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    var loginState = mutableStateOf(LoginState())
        private set

    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            // Email/Mobile changed
            is LoginUiEvent.EmailOrMobileChanged -> {
                loginState.value = loginState.value.copy(
                    emailOrMobile = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailOrMobileErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailOrMobileEmptyErrorState
                    )
                )
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            // Submit Login
            is LoginUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    // TODO Trigger login in authentication flow
                    val emailOrMobileString = loginState.value.emailOrMobile.trim()
                    val passwordString = loginState.value.password
                    loginUser(emailOrMobileString, passwordString)

                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailOrMobileString = loginState.value.emailOrMobile.trim()
        val passwordString = loginState.value.password
        return when {

            // Email/Mobile empty
            emailOrMobileString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        emailOrMobileErrorState = emailOrMobileEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }

    private fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect{result ->
            when(result){
                is Resource.Success -> {
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                    LoginScreenActions.Home
                }
                is Resource.Loading -> {
                    //Toast.makeText(coroutineContext, "")
                }
                is Resource.Error -> {
                    loginState.value = loginState.value.copy(
                        errorState = LoginErrorState(
                            passwordErrorState = passwordEmptyErrorState,
                            emailOrMobileErrorState = emailOrMobileEmptyErrorState
                        )
                    )

                }
            }
        }
    }

    fun logOutUser(){
        firebaseAuth.signOut()
    }


    //auto logout when loaded
    init {
        firebaseAuth.signOut()
    }

}