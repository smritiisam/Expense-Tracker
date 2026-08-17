package com.example.expensetracker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expensetracker.payment.PaymentController

class HomeViewModel : ViewModel() {

    var amount by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onAmountChanged(newAmount: String) {
        amount = newAmount
        errorMessage = null
    }

    fun payViaUpi(
        paymentController: PaymentController
    ) {

        val value = amount.toDoubleOrNull()

        if (value == null || value <= 0) {
            errorMessage = "Enter an amount greater than ₹0"
            return
        }

        val launched = paymentController.launchUpiPayment(
            amount = amount
        )

        if (!launched) {
            errorMessage = "No compatible UPI app found"
        }
    }
}