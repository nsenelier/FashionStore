package com.cyberwalker.fashionstore.signup.state
/**
 * Registration Screen Events
 */
sealed class SignupUiEvent {
    data class EmailChanged(val inputValue: String) : SignupUiEvent()
    data class MobileNumberChanged(val inputValue: String) : SignupUiEvent()
    data class PasswordChanged(val inputValue: String) : SignupUiEvent()
    data class ConfirmPasswordChanged(val inputValue: String) : SignupUiEvent()
    object Submit : SignupUiEvent()
}