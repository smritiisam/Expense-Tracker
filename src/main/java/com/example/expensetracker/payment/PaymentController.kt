package com.samm.expense_tracker.payment

import android.content.Context

data class UpiApp(
    val name: String,
    val packageName: String
)

class PaymentController(
    private val context: Context
) {

    private val supportedUpiApps = listOf(
        UpiApp(
            name = "Google Pay",
            packageName = "com.google.android.apps.nbu.paisa.user"
        ),
        UpiApp(
            name = "PhonePe",
            packageName = "com.phonepe.app"
        ),
        UpiApp(
            name = "Paytm",
            packageName = "net.one97.paytm"
        ),
        UpiApp(
            name = "BHIM",
            packageName = "in.org.npci.upiapp"
        )
    )

    fun getInstalledUpiApps(): List<UpiApp> {

        val packageManager = context.packageManager

        return supportedUpiApps.filter { app ->

            packageManager.getLaunchIntentForPackage(
                app.packageName
            ) != null
        }
    }

    fun openUpiApp(packageName: String): Boolean {

        val launchIntent = context.packageManager
            .getLaunchIntentForPackage(packageName)

        return if (launchIntent != null) {

            context.startActivity(launchIntent)

            true

        } else {

            false
        }
    }
}