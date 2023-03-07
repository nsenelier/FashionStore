package com.cyberwalker.fashionstore.login.state

import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.login.state.ErrorState

val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_email_mobile
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)