package com.example.expense_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.expense_tracker.payment.PaymentController
import com.example.expense_tracker.ui.HomeScreen
import com.example.expense_tracker.ui.HomeViewModel
import com.example.expense_tracker.ui.theme.ExpenseTrackerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.expense_tracker.ui.CategoriesScreen
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
                var showCategories by remember {
                    mutableStateOf(false)
                }
                if (showCategories) {

                    CategoriesScreen(
                        viewModel = viewModel,
                        onBack = {
                            showCategories = false
                        }
                    )

                } else {

                    HomeScreen(

                        viewModel = viewModel,


                        onPayClick = {

                            viewModel.onPayClicked(
                                paymentController
                            )
                        },
                        onCategoriesClick = {
                            showCategories = true
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
}