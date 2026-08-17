package com.example.expense_tracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Button(
                onClick = onBack,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Back")
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Category"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Expense Categories",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            if (viewModel.categories.isEmpty()) {

                Text(
                    text = "No categories created yet."
                )

            } else {

                LazyColumn {

                    items(viewModel.categories) { category ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = category.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "₹${category.amount}"
                                )
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

            onAdd = { name, amount ->

                val success = viewModel.addCategory(
                    name = name,
                    amount = amount
                )

                if (success) {
                    showDialog = false
                }
            }
        )
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {

    var categoryName by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableStateOf("")
    }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text("Create Category")
        },

        text = {

            Column {

                OutlinedTextField(
                    value = categoryName,
                    onValueChange = {
                        categoryName = it
                    },
                    label = {
                        Text("Category name")
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                    },
                    label = {
                        Text("Assigned amount")
                    },
                    prefix = {
                        Text("₹")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
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
                }
            ) {
                Text("Create")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}