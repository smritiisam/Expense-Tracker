package com.samm.expense_tracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samm.expense_tracker.ui.theme.*


@Composable
fun CategoriesScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {

    var showDialog by remember {
        mutableStateOf(false)
    }


    val totalAssigned =
        viewModel.categories.sumOf {
            it.assignedAmount
        }


    val totalRemaining =
        viewModel.categories.sumOf {
            it.remainingAmount
        }


    Scaffold(

        containerColor =
            PremiumNavy,

        floatingActionButton = {

            FloatingActionButton(

                onClick = {
                    showDialog = true
                },

                containerColor =
                    Emerald,

                contentColor =
                    PremiumNavy,

                shape =
                    CircleShape
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Add,

                    contentDescription =
                        "Add Category"
                )
            }
        }

    ) { paddingValues ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            PremiumNavy,
                            PremiumNavyLight
                        )
                    )
                )
                .padding(paddingValues)
        ) {


            LazyColumn(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 20.dp
                        ),

                contentPadding =
                    PaddingValues(
                        bottom = 100.dp
                    )
            ) {


                item {

                    Spacer(
                        modifier =
                            Modifier.height(28.dp)
                    )


                    TextButton(
                        onClick =
                            onBack
                    ) {

                        Text(
                            text = "←  Back",
                            color = Emerald
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    Text(
                        text =
                            "Your Budget",

                        style =
                            MaterialTheme
                                .typography
                                .headlineLarge,

                        color =
                            PremiumWhite,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Text(
                        text =
                            "Keep track of what you have left.",

                        color =
                            PremiumGray
                    )


                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )


                    // Summary card

                    Card(

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                24.dp
                            ),

                        colors =
                            CardDefaults
                                .cardColors(
                                    containerColor =
                                        CardDark
                                )
                    ) {

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        20.dp
                                    ),

                            horizontalArrangement =
                                Arrangement
                                    .SpaceBetween
                        ) {


                            Column {

                                Text(
                                    "Assigned",
                                    color =
                                        PremiumGray
                                )

                                Text(
                                    text =
                                        "₹${formatAmount(totalAssigned)}",

                                    color =
                                        PremiumWhite,

                                    fontWeight =
                                        FontWeight.Bold,

                                    fontSize =
                                        22.sp
                                )
                            }


                            Column(

                                horizontalAlignment =
                                    Alignment.End
                            ) {

                                Text(
                                    "Remaining",
                                    color =
                                        PremiumGray
                                )

                                Text(
                                    text =
                                        "₹${formatAmount(totalRemaining)}",

                                    color =
                                        Emerald,

                                    fontWeight =
                                        FontWeight.Bold,

                                    fontSize =
                                        22.sp
                                )
                            }
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(26.dp)
                    )
                }


                if (
                    viewModel.categories
                        .isEmpty()
                ) {

                    item {

                        Card(

                            modifier =
                                Modifier
                                    .fillMaxWidth(),

                            shape =
                                RoundedCornerShape(
                                    22.dp
                                ),

                            colors =
                                CardDefaults
                                    .cardColors(
                                        containerColor =
                                            CardDark
                                    )
                        ) {

                            Column(

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            30.dp
                                        ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "₹",

                                    fontSize =
                                        42.sp,

                                    color =
                                        Emerald,

                                    fontWeight =
                                        FontWeight.Bold
                                )


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            8.dp
                                        )
                                )


                                Text(
                                    text =
                                        "No categories yet",

                                    color =
                                        PremiumWhite,

                                    fontWeight =
                                        FontWeight.Bold
                                )


                                Text(
                                    text =
                                        "Tap + to create your first budget category.",

                                    color =
                                        PremiumGray
                                )
                            }
                        }
                    }

                } else {


                    items(
                        viewModel.categories,
                        key = {
                            it.id
                        }
                    ) { category ->


                        var debitAmount
                                by remember(
                                    category.id
                                ) {

                                    mutableStateOf(
                                        viewModel.amount
                                    )
                                }


                        var debitError
                                by remember(
                                    category.id
                                ) {

                                    mutableStateOf<String?>(
                                        null
                                    )
                                }


                        val progress =

                            if (
                                category
                                    .assignedAmount >
                                0
                            ) {

                                (
                                        category
                                            .remainingAmount /
                                                category
                                                    .assignedAmount
                                        )
                                    .toFloat()
                                    .coerceIn(
                                        0f,
                                        1f
                                    )

                            } else {

                                0f
                            }


                        Card(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical =
                                            8.dp
                                    ),

                            shape =
                                RoundedCornerShape(
                                    24.dp
                                ),

                            colors =
                                CardDefaults
                                    .cardColors(
                                        containerColor =
                                            CardDark
                                    ),

                            elevation =
                                CardDefaults
                                    .cardElevation(
                                        defaultElevation =
                                            6.dp
                                    )
                        ) {


                            Column(

                                modifier =
                                    Modifier.padding(
                                        20.dp
                                    )
                            ) {


                                Row(

                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),

                                    horizontalArrangement =
                                        Arrangement
                                            .SpaceBetween,

                                    verticalAlignment =
                                        Alignment
                                            .CenterVertically
                                ) {


                                    Box(

                                        modifier =
                                            Modifier
                                                .size(
                                                    44.dp
                                                )
                                                .clip(
                                                    CircleShape
                                                )
                                                .background(
                                                    CardDarkSecondary
                                                ),

                                        contentAlignment =
                                            Alignment
                                                .Center
                                    ) {

                                        Text(
                                            text = "₹",

                                            color =
                                                Gold,

                                            fontWeight =
                                                FontWeight
                                                    .Bold
                                        )
                                    }


                                    Spacer(
                                        modifier =
                                            Modifier.width(
                                                14.dp
                                            )
                                    )


                                    Text(

                                        text =
                                            category.name,

                                        color =
                                            PremiumWhite,

                                        fontWeight =
                                            FontWeight.Bold,

                                        fontSize =
                                            19.sp,

                                        modifier =
                                            Modifier.weight(
                                                1f
                                            )
                                    )
                                }


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            18.dp
                                        )
                                )


                                Row(

                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),

                                    horizontalArrangement =
                                        Arrangement
                                            .SpaceBetween
                                ) {


                                    BudgetChip(
                                        title =
                                            "Assigned",

                                        amount =
                                            category
                                                .assignedAmount,

                                        highlight =
                                            false
                                    )


                                    BudgetChip(
                                        title =
                                            "Remaining",

                                        amount =
                                            category
                                                .remainingAmount,

                                        highlight =
                                            true
                                    )
                                }


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            18.dp
                                        )
                                )


                                Text(
                                    text =
                                        "${(progress * 100).toInt()}% remaining",

                                    color =
                                        PremiumGray,

                                    fontSize =
                                        12.sp
                                )


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            7.dp
                                        )
                                )


                                LinearProgressIndicator(

                                    progress = {
                                        progress
                                    },

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(
                                                8.dp
                                            )
                                            .clip(
                                                RoundedCornerShape(
                                                    10.dp
                                                )
                                            ),

                                    color =
                                        Emerald,

                                    trackColor =
                                        CardDarkSecondary
                                )


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            22.dp
                                        )
                                )


                                OutlinedTextField(

                                    value =
                                        debitAmount,

                                    onValueChange = {

                                        debitAmount =
                                            it

                                        debitError =
                                            null
                                    },

                                    label = {
                                        Text(
                                            "Debit amount"
                                        )
                                    },

                                    prefix = {

                                        Text(
                                            "₹ ",
                                            color =
                                                Emerald
                                        )
                                    },

                                    keyboardOptions =
                                        KeyboardOptions(
                                            keyboardType =
                                                KeyboardType
                                                    .Decimal
                                        ),

                                    singleLine = true,

                                    shape =
                                        RoundedCornerShape(
                                            14.dp
                                        ),

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                )


                                debitError
                                    ?.let {

                                        Spacer(
                                            modifier =
                                                Modifier
                                                    .height(
                                                        6.dp
                                                    )
                                        )

                                        Text(
                                            text = it,
                                            color =
                                                ErrorRed,

                                            fontSize =
                                                12.sp
                                        )
                                    }


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            12.dp
                                        )
                                )


                                Button(

                                    onClick = {

                                        val error =
                                            viewModel
                                                .debitCategory(

                                                    categoryId =
                                                        category.id,

                                                    debitAmount =
                                                        debitAmount
                                                )


                                        if (
                                            error ==
                                            null
                                        ) {

                                            debitAmount =
                                                ""

                                        } else {

                                            debitError =
                                                error
                                        }
                                    },

                                    colors =
                                        ButtonDefaults
                                            .buttonColors(
                                                containerColor =
                                                    Emerald,

                                                contentColor =
                                                    PremiumNavy
                                            ),

                                    shape =
                                        RoundedCornerShape(
                                            14.dp
                                        ),

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(
                                                50.dp
                                            )
                                ) {

                                    Text(
                                        text =
                                            "ADD DEBIT",

                                        fontWeight =
                                            FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    if (showDialog) {

        AddCategoryDialog(

            onDismiss = {
                showDialog = false
            },

            onAdd = {
                    name,
                    amount ->

                val success =
                    viewModel.addCategory(
                        name = name,
                        amount = amount
                    )

                if (success) {

                    showDialog =
                        false
                }
            }
        )
    }
}

@Composable
private fun BudgetChip(
    title: String,
    amount: Double,
    highlight: Boolean
) {

    Column(

        modifier =
            Modifier
                .clip(
                    RoundedCornerShape(
                        14.dp
                    )
                )
                .background(
                    CardDarkSecondary
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
    ) {

        Text(
            text = title,

            color =
                PremiumGray,

            fontSize =
                11.sp
        )


        Text(
            text =
                "₹${formatAmount(amount)}",

            color =
                if (highlight)
                    Emerald
                else
                    PremiumWhite,

            fontWeight =
                FontWeight.Bold,

            fontSize =
                16.sp
        )
    }
}


private fun formatAmount(
    value: Double
): String {

    return if (
        value % 1.0 == 0.0
    ) {

        value
            .toLong()
            .toString()

    } else {

        "%.2f".format(value)
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {

    var categoryName
            by remember {
                mutableStateOf("")
            }


    var amount
            by remember {
                mutableStateOf("")
            }


    AlertDialog(

        onDismissRequest =
            onDismiss,

        containerColor =
            CardDark,

        shape =
            RoundedCornerShape(
                24.dp
            ),

        title = {

            Text(
                text =
                    "Create Category",

                color =
                    PremiumWhite,

                fontWeight =
                    FontWeight.Bold
            )
        },

        text = {

            Column {

                Text(
                    text =
                        "Set a monthly budget for this expense.",

                    color =
                        PremiumGray
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            18.dp
                        )
                )


                OutlinedTextField(

                    value =
                        categoryName,

                    onValueChange = {
                        categoryName =
                            it
                    },

                    label = {
                        Text(
                            "Category name"
                        )
                    },

                    singleLine =
                        true
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            12.dp
                        )
                )


                OutlinedTextField(

                    value =
                        amount,

                    onValueChange = {
                        amount = it
                    },

                    label = {
                        Text(
                            "Assigned amount"
                        )
                    },

                    prefix = {

                        Text(
                            "₹ ",
                            color =
                                Emerald
                        )
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType
                                    .Decimal
                        ),

                    singleLine =
                        true
                )
            }
        },

        confirmButton = {

            Button(

                onClick = {

                    onAdd(
                        categoryName,
                        amount
                    )
                },

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                Emerald,

                            contentColor =
                                PremiumNavy
                        )
            ) {

                Text(
                    "CREATE",
                    fontWeight =
                        FontWeight.Bold
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick =
                    onDismiss
            ) {

                Text(
                    "Cancel",
                    color =
                        PremiumGray
                )
            }
        }
    )
}