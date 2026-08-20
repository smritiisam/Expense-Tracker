package com.samm.expense_tracker.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onSendOtp: () -> Unit,
    onVerifyOtp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text = "Login",
            style =
                MaterialTheme
                    .typography
                    .headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = viewModel.phoneNumber,

            onValueChange =
                viewModel::onPhoneNumberChanged,

            label = {
                Text("Phone number")
            },

            prefix = {
                Text("+91 ")
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Phone
                ),

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onSendOtp,
            enabled = !viewModel.isLoading,
            modifier =
                Modifier.fillMaxWidth()
        ) {
            Text("Send OTP")
        }

        if (
            viewModel.verificationId != null
        ) {

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            OutlinedTextField(
                value = viewModel.otp,

                onValueChange =
                    viewModel::onOtpChanged,

                label = {
                    Text("OTP")
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    ),

                modifier =
                    Modifier.fillMaxWidth()
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Button(
                onClick = onVerifyOtp,
                enabled =
                    !viewModel.isLoading,

                modifier =
                    Modifier.fillMaxWidth()
            ) {
                Text("Verify & Login")
            }
        }

        viewModel.errorMessage?.let {

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text = it,

                color =
                    MaterialTheme
                        .colorScheme
                        .error
            )
        }

        if (viewModel.isLoading) {

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            CircularProgressIndicator()
        }
    }
}