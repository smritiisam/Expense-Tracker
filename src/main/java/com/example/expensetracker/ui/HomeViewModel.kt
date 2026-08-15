package com.example.expensetracker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var amount by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onAmountChanged(newAmount: String) {
        amount = newAmount
        errorMessage = null
    }

    fun validateAmount(): Boolean {

        val value = amount.toDoubleOrNull()

        return if (value == null || value <= 0) {
            errorMessage = "Enter an amount greater than ₹0"
            false
        } else {
            errorMessage = null
            true
        }
    }
}