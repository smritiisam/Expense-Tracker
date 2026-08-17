package com.example.expense_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.expense_tracker.payment.PaymentController
import com.example.expense_tracker.ui.HomeScreen
import com.example.expense_tracker.ui.HomeViewModel
import com.example.expense_tracker.ui.theme.ExpenseTrackerTheme


class MainActivity : ComponentActivity() {

    private val viewModel:
            HomeViewModel by viewModels()


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)


        val paymentController =
            PaymentController(this)


        setContent {

            ExpenseTrackerTheme {

                HomeScreen(

                    viewModel = viewModel,


                    onPayClick = {

                        viewModel.onPayClicked(
                            paymentController
                        )
                    },


                    onUpiAppSelected = { app ->

                        viewModel.selectUpiApp(
                            app = app,
                            paymentController =
                                paymentController
                        )
                    }
                )
            }
        }
    }
}