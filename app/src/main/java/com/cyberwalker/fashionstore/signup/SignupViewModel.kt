package com.cyberwalker.fashionstore.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.login.LoginScreenActions
import com.cyberwalker.fashionstore.login.state.ErrorState
import com.cyberwalker.fashionstore.login.state.Resource
import com.cyberwalker.fashionstore.login.state.emailOrMobileEmptyErrorState
import com.cyberwalker.fashionstore.repository.AuthRepository
import com.cyberwalker.fashionstore.signup.state.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
    ): ViewModel() {

    var signupState = mutableStateOf(SignupState())
        private set

    /**
     * Function called on any login event [signupUiEvent]
     */
    fun onUiEvent(signupUiEvent: SignupUiEvent) {
        when (signupUiEvent) {

            // Email id changed event
            is SignupUiEvent.EmailChanged -> {
                signupState.value = signupState.value.copy(
                    emailId = signupUiEvent.inputValue,
                    errorState = signupState.value.errorState.copy(
                        emailIdErrorState = if (signupUiEvent.inputValue.trim().isEmpty()) {
                            // Email id empty state
                            emailEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Mobile Number changed event
            is SignupUiEvent.MobileNumberChanged -> {
                signupState.value = signupState.value.copy(
                    mobileNumber = signupUiEvent.inputValue,
                    errorState = signupState.value.errorState.copy(
                        mobileNumberErrorState = if (signupUiEvent.inputValue.trim()
                                .isEmpty()
                        ) {
                            // Mobile Number Empty state
                            mobileNumberEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Password changed event
            is SignupUiEvent.PasswordChanged -> {
                signupState.value = signupState.value.copy(
                    password = signupUiEvent.inputValue,
                    errorState = signupState.value.errorState.copy(
                        passwordErrorState = if (signupUiEvent.inputValue.trim().isEmpty()) {
                            // Password Empty state
                            passwordEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Confirm Password changed event
            is SignupUiEvent.ConfirmPasswordChanged -> {
                signupState.value = signupState.value.copy(
                    confirmPassword = signupUiEvent.inputValue,
                    errorState = signupState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            signupUiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            signupState.value.password.trim() != signupUiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }

            // Submit signup event
            is SignupUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    // TODO Trigger signup in authentication flow
                    val emailString = signupState.value.emailId.trim()
                    val passwordString = signupState.value.password.trim()

                    registerUser(emailString, passwordString)
                    signupState.value = signupState.value.copy(isSignupSuccessful = true)
                }
            }
        }
    }

    /**
     * Function to validate inputs
     * Ideally it should be on domain layer (usecase)
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private fun validateInputs(): Boolean {
        val emailString = signupState.value.emailId.trim()
        val mobileNumberString = signupState.value.mobileNumber.trim()
        val passwordString = signupState.value.password.trim()
        val confirmPasswordString = signupState.value.confirmPassword.trim()

        return when {

            // Email empty
            emailString.isEmpty() -> {
                signupState.value = signupState.value.copy(
                    errorState = SignupErrorState(
                        emailIdErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            //Mobile Number Empty
            mobileNumberString.isEmpty() -> {
                signupState.value = signupState.value.copy(
                    errorState = SignupErrorState(
                        mobileNumberErrorState = mobileNumberEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                signupState.value = signupState.value.copy(
                    errorState = SignupErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            //Confirm Password Empty
            confirmPasswordString.isEmpty() -> {
                signupState.value = signupState.value.copy(
                    errorState = SignupErrorState(
                        confirmPasswordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }

            // Password and Confirm Password are different
            passwordString != confirmPasswordString -> {
                signupState.value = signupState.value.copy(
                    errorState = SignupErrorState(
                        confirmPasswordErrorState = passwordMismatchErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                signupState.value =
                    signupState.value.copy(errorState = SignupErrorState())
                true
            }
        }
    }

    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect{result ->
            when(result){
                is Resource.Success -> {
                    signupState.value = signupState.value.copy(isSignupSuccessful = true)
                    SignupScreenActions.Login
                }
                is Resource.Loading -> {
                    //Toast.makeText(coroutineContext, "")
                }
                is Resource.Error -> {
                    signupState.value = signupState.value.copy(
                        errorState = SignupErrorState(
                            passwordErrorState = passwordEmptyErrorState,
                            emailIdErrorState = emailEmptyErrorState,
                            mobileNumberErrorState = mobileNumberEmptyErrorState,
                            confirmPasswordErrorState = confirmPasswordEmptyErrorState

                        )
                    )

                }
            }
        }
    }
}