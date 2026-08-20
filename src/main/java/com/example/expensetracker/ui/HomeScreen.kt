package com.samm.expense_tracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samm.expense_tracker.payment.UpiApp
import com.samm.expense_tracker.ui.theme.*


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onPayClick: () -> Unit,
    onUpiAppSelected: (UpiApp) -> Unit,
    onCategoriesClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PremiumNavy,
                        PremiumNavyLight
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp
                )
        ) {

            Spacer(
                modifier =
                    Modifier.height(70.dp)
            )


            // Premium wallet-style icon

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Emerald),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = "₹",

                    fontSize = 34.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color = PremiumNavy
                )
            }


            Spacer(
                modifier =
                    Modifier.height(26.dp)
            )


            Text(
                text = "Expense Tracker",

                style =
                    MaterialTheme
                        .typography
                        .headlineLarge,

                fontWeight =
                    FontWeight.Bold,

                color = PremiumWhite
            )


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            Text(
                text =
                    "Pay smarter. Know where your money goes.",

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,

                color = PremiumGray
            )


            Spacer(
                modifier =
                    Modifier.height(38.dp)
            )


            // Amount Card

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(28.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            CardDark
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(22.dp)
                ) {

                    Text(
                        text =
                            "PAYMENT AMOUNT",

                        color = PremiumGray,

                        fontSize = 12.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    OutlinedTextField(

                        value =
                            viewModel.amount,

                        onValueChange =
                            viewModel::onAmountChanged,

                        label = {
                            Text(
                                "Enter amount"
                            )
                        },

                        prefix = {

                            Text(
                                text = "₹ ",
                                color = Emerald,
                                fontWeight =
                                    FontWeight.Bold
                            )
                        },

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Decimal
                            ),

                        singleLine = true,

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        modifier =
                            Modifier.fillMaxWidth()
                    )


                    viewModel
                        .errorMessage
                        ?.let { message ->

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        10.dp
                                    )
                            )

                            Text(
                                text = message,
                                color = ErrorRed,
                                fontSize = 13.sp
                            )
                        }


                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )


                    Button(

                        onClick =
                            onPayClick,

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            ButtonDefaults
                                .buttonColors(
                                    containerColor =
                                        Emerald,

                                    contentColor =
                                        PremiumNavy
                                ),

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                    ) {

                        Text(
                            text = "PAY VIA UPI",

                            fontWeight =
                                FontWeight.Bold,

                            fontSize = 16.sp
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    OutlinedButton(

                        onClick =
                            onCategoriesClick,

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            ButtonDefaults
                                .outlinedButtonColors(
                                    contentColor =
                                        Gold
                                ),

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                    ) {

                        Text(
                            text =
                                "VIEW CATEGORIES",

                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }
                }
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            Text(
                text =
                    "Budget • Track • Stay in control",

                color = PremiumGray,

                fontSize = 13.sp,

                modifier =
                    Modifier.align(
                        Alignment.CenterHorizontally
                    )
            )
        }
    }


    // Existing UPI chooser

    if (
        viewModel.showUpiChooser
    ) {

        AlertDialog(

            onDismissRequest = {
                viewModel
                    .dismissUpiChooser()
            },

            containerColor =
                CardDark,

            title = {

                Text(
                    text =
                        "Choose UPI App",

                    color =
                        PremiumWhite
                )
            },

            text = {

                Column {

                    viewModel
                        .installedUpiApps
                        .forEach { app ->

                            Card(

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            vertical =
                                                4.dp
                                        )
                                        .clickable {

                                            onUpiAppSelected(
                                                app
                                            )
                                        },

                                colors =
                                    CardDefaults
                                        .cardColors(
                                            containerColor =
                                                CardDarkSecondary
                                        )
                            ) {

                                Text(
                                    text =
                                        app.name,

                                    color =
                                        PremiumWhite,

                                    modifier =
                                        Modifier
                                            .padding(
                                                18.dp
                                            )
                                )
                            }
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

                    Text(
                        "Cancel",
                        color = Emerald
                    )
                }
            }
        )
    }
}