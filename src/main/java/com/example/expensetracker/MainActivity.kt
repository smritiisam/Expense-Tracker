package com.samm.expense_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samm.expense_tracker.auth.AuthViewModel
import com.samm.expense_tracker.auth.LoginScreen
import com.samm.expense_tracker.payment.PaymentController
import com.samm.expense_tracker.ui.CategoriesScreen
import com.samm.expense_tracker.ui.HomeScreen
import com.samm.expense_tracker.ui.HomeViewModel
import com.samm.expense_tracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {

    private val homeViewModel:
            HomeViewModel by viewModels()

    private val authViewModel:
            AuthViewModel by viewModels()


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )


        val paymentController =
            PaymentController(this)


        setContent {

            ExpenseTrackerTheme {

                if (
                    !authViewModel.isLoggedIn
                ) {

                    LoginScreen(

                        viewModel =
                            authViewModel,

                        onSendOtp = {

                            authViewModel
                                .sendOtp(this)
                        },

                        onVerifyOtp = {

                            authViewModel
                                .verifyOtp()
                        }
                    )

                } else {

                    LaunchedEffect(
                        authViewModel
                            .isLoggedIn
                    ) {

                        homeViewModel
                            .loadCategoriesForCurrentUser()
                    }


                    var showCategories
                            by remember {
                                mutableStateOf(false)
                            }


                    if (showCategories) {

                        CategoriesScreen(

                            viewModel =
                                homeViewModel,

                            onBack = {

                                showCategories =
                                    false
                            }
                        )

                    } else {

                        HomeScreen(

                            viewModel =
                                homeViewModel,

                            onPayClick = {

                                homeViewModel
                                    .onPayClicked(
                                        paymentController
                                    )
                            },

                            onCategoriesClick = {

                                showCategories =
                                    true
                            },

                            onUpiAppSelected = {
                                    app ->

                                homeViewModel
                                    .selectUpiApp(
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
}