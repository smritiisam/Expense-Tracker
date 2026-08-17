package com.example.expense_tracker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expense_tracker.payment.PaymentController
import com.example.expense_tracker.payment.UpiApp

class HomeViewModel : ViewModel() {

    var amount by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var installedUpiApps by mutableStateOf<List<UpiApp>>(emptyList())
        private set

    var showUpiChooser by mutableStateOf(false)
        private set


    fun onAmountChanged(newAmount: String) {
        amount = newAmount
        errorMessage = null
    }


    fun onPayClicked(
        paymentController: PaymentController
    ) {

        val value = amount.toDoubleOrNull()

        if (value == null || value <= 0) {
            errorMessage = "Enter an amount greater than ₹0"
            return
        }


        installedUpiApps =
            paymentController.getInstalledUpiApps()


        if (installedUpiApps.isEmpty()) {
            errorMessage = "No supported UPI app found"
            return
        }


        showUpiChooser = true
    }


    fun dismissUpiChooser() {
        showUpiChooser = false
    }


    fun selectUpiApp(
        app: UpiApp,
        paymentController: PaymentController
    ) {

        showUpiChooser = false

        val opened =
            paymentController.openUpiApp(
                app.packageName
            )

        if (!opened) {
            errorMessage = "Unable to open ${app.name}"
        }
    }
}