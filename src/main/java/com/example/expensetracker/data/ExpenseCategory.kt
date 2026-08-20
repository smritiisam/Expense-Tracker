package com.samm.expense_tracker.data


data class ExpenseCategory(
    val id: String = "",
    val name: String = "",
    val assignedAmount: Double = 0.0,
    val remainingAmount: Double = 0.0
)