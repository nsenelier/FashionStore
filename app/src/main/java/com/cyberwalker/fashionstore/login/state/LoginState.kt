package com.cyberwalker.fashionstore.login.state

import androidx.annotation.StringRes

/**
 * Login State holding ui input values
 */
data class LoginState(
    val emailOrMobile: String = "",
    val password: String = "",
    val errorState: LoginErrorState = LoginErrorState(),
    val isLoginSuccessful: Boolean = false
)

/**
 * Error state in login holding respective
 * text field validation errors
 */
data class LoginErrorState(
    val emailOrMobileErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState()
)

data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = 0
)