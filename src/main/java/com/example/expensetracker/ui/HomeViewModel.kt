package com.samm.expense_tracker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.samm.expense_tracker.data.CategoryRepository
import com.samm.expense_tracker.data.ExpenseCategory
import com.samm.expense_tracker.payment.PaymentController
import com.samm.expense_tracker.payment.UpiApp

class HomeViewModel : ViewModel() {

    private val categoryRepository =
        CategoryRepository()


    var categories by
    mutableStateOf<List<ExpenseCategory>>(
        emptyList()
    )
        private set


    var amount by mutableStateOf("")
        private set


    var errorMessage by
    mutableStateOf<String?>(null)
        private set


    var installedUpiApps by
    mutableStateOf<List<UpiApp>>(
        emptyList()
    )
        private set


    var showUpiChooser by
    mutableStateOf(false)
        private set


    fun loadCategoriesForCurrentUser() {

        categoryRepository
            .listenToCategories {
                    loadedCategories ->

                categories =
                    loadedCategories
            }
    }


    fun addCategory(
        name: String,
        amount: String
    ): Boolean {

        val amountValue =
            amount.toDoubleOrNull()

        if (
            name.isBlank() ||
            amountValue == null ||
            amountValue <= 0
        ) {
            return false
        }

        categoryRepository.addCategory(
            name = name.trim(),
            assignedAmount =
                amountValue
        )

        return true
    }


    fun debitCategory(
        categoryId: String,
        debitAmount: String
    ): String? {

        val debitValue =
            debitAmount.toDoubleOrNull()

        if (
            debitValue == null ||
            debitValue <= 0
        ) {
            return "Enter a valid debit amount"
        }

        val category =
            categories.find {
                it.id == categoryId
            }
                ?: return "Category not found"


        if (
            debitValue >
            category.remainingAmount
        ) {
            return "Debit exceeds remaining amount"
        }


        val newRemaining =
            category.remainingAmount -
                    debitValue


        categoryRepository
            .updateRemainingAmount(
                categoryId =
                    category.id,

                remainingAmount =
                    newRemaining
            )


        return null
    }


    fun onAmountChanged(
        newAmount: String
    ) {

        amount = newAmount
        errorMessage = null
    }


    fun onPayClicked(
        paymentController:
        PaymentController
    ) {

        val value =
            amount.toDoubleOrNull()

        if (
            value == null ||
            value <= 0
        ) {

            errorMessage =
                "Enter an amount greater than ₹0"

            return
        }


        installedUpiApps =
            paymentController
                .getInstalledUpiApps()


        if (
            installedUpiApps.isEmpty()
        ) {

            errorMessage =
                "No supported UPI app found"

            return
        }


        showUpiChooser = true
    }


    fun dismissUpiChooser() {

        showUpiChooser = false
    }


    fun selectUpiApp(
        app: UpiApp,

        paymentController:
        PaymentController
    ) {

        showUpiChooser = false


        val opened =
            paymentController.openUpiApp(
                app.packageName
            )


        if (!opened) {

            errorMessage =
                "Unable to open ${app.name}"
        }
    }


    override fun onCleared() {

        categoryRepository
            .stopListening()

        super.onCleared()
    }
}