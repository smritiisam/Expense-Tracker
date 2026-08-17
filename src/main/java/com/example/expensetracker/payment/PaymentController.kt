package com.example.expensetracker.payment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class PaymentController(
    private val context: Context
) {

    fun launchUpiPayment(amount: String): Boolean {

        val upiUri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", "your-vpa@bank")
            .appendQueryParameter("pn", "Expense Tracker")
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()

        val upiIntent = Intent(
            Intent.ACTION_VIEW,
            upiUri
        )

        val chooser = Intent.createChooser(
            upiIntent,
            "Pay with"
        )

        return try {
            context.startActivity(chooser)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }
}