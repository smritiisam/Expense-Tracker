package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.expensetracker.payment.PaymentController
import com.example.expensetracker.ui.HomeScreen
import com.example.expensetracker.ui.HomeViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val paymentController = PaymentController(this)

        setContent {

            HomeScreen(
                viewModel = viewModel,
                onPayClick = {
                    viewModel.payViaUpi(
                        paymentController
                    )
                }
            )
        }
    }
}