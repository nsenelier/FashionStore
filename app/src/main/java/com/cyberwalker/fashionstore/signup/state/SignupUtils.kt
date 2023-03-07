package com.cyberwalker.fashionstore.signup.state

import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.login.state.ErrorState


val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.signup_error_msg_empty_email
)

val mobileNumberEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.signup_error_msg_empty_mobile
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.signup_error_msg_empty_password
)

val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.signup_error_msg_empty_confirm_password
)

val passwordMismatchErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.signup_error_msg_password_mismatch
)
