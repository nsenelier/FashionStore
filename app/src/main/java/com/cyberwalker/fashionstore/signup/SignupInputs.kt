package com.cyberwalker.fashionstore.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.login.common.EmailTextField
import com.cyberwalker.fashionstore.login.common.MobileNumberTextField
import com.cyberwalker.fashionstore.login.common.NormalButton
import com.cyberwalker.fashionstore.login.common.PasswordTextField
import com.cyberwalker.fashionstore.signup.state.SignupState
import com.cyberwalker.fashionstore.ui.theme.AppTheme

@Composable
fun SignupInputs(
    signupState: SignupState,
    onEmailIdChange: (String) -> Unit,
    onMobileNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    // Login Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email ID
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = signupState.emailId,
            onValueChange = onEmailIdChange,
            label = stringResource(id = R.string.signup_email_label),
            isError = signupState.errorState.emailIdErrorState.hasError,
            errorText = stringResource(id = signupState.errorState.emailIdErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )

        // Mobile Number
        MobileNumberTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = signupState.mobileNumber,
            onValueChange = onMobileNumberChange,
            label = stringResource(id = R.string.signup_mobile_label),
            isError = signupState.errorState.mobileNumberErrorState.hasError,
            errorText = stringResource(id = signupState.errorState.mobileNumberErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )


        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = signupState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.signup_password_label),
            isError = signupState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = signupState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )

        // Confirm Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = signupState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(id = R.string.signup_confirm_password_label),
            isError = signupState.errorState.confirmPasswordErrorState.hasError,
            errorText = stringResource(id = signupState.errorState.confirmPasswordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

        // Signup Submit Button
        NormalButton(
            modifier = Modifier.padding(top = AppTheme.dimens.paddingExtraLarge),
            text = stringResource(id = R.string.signup_button_text),
            onClick = onSubmit
        )


    }
}