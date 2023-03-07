package com.cyberwalker.fashionstore.signup.state

import com.cyberwalker.fashionstore.login.state.ErrorState

/**
 * signup State holding ui input values
 */
data class SignupState(
    val emailId: String = "",
    val mobileNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: SignupErrorState = SignupErrorState(),
    val isSignupSuccessful: Boolean = false
)

/**
 * Error state in signup holding respective
 * text field validation errors
 */
data class SignupErrorState(
    val emailIdErrorState: ErrorState = ErrorState(),
    val mobileNumberErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)