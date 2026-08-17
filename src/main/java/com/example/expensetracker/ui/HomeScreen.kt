package com.example.expense_tracker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expense_tracker.payment.UpiApp


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onPayClick: () -> Unit,
    onUpiAppSelected: (UpiApp) -> Unit,
    onCategoriesClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Expense Tracker",
            style = MaterialTheme.typography.headlineMedium
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        OutlinedTextField(
            value = viewModel.amount,

            onValueChange =
                viewModel::onAmountChanged,

            label = {
                Text("Amount")
            },

            prefix = {
                Text("₹")
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Decimal
                ),

            modifier =
                Modifier.fillMaxWidth()
        )


        viewModel.errorMessage?.let { message ->

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text = message,
                color =
                    MaterialTheme
                        .colorScheme
                        .error
            )
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        Button(
            onClick = onPayClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Pay via UPI")
        }
        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onCategoriesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CATEGORIES")
        }
    }


    if (viewModel.showUpiChooser) {

        AlertDialog(

            onDismissRequest = {
                viewModel.dismissUpiChooser()
            },

            title = {
                Text("Choose UPI App")
            },

            text = {

                Column {

                    viewModel.installedUpiApps
                        .forEach { app ->

                            Text(
                                text = app.name,

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onUpiAppSelected(app)
                                    }
                                    .padding(16.dp)
                            )
                        }
                }
            },

            confirmButton = {},

            dismissButton = {

                TextButton(
                    onClick = {
                        viewModel
                            .dismissUpiChooser()
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}