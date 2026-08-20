package com.samm.expense_tracker.data


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class CategoryRepository {

    private val auth =
        FirebaseAuth.getInstance()

    private val firestore =
        FirebaseFirestore.getInstance()

    private var listenerRegistration:
            ListenerRegistration? = null


    private fun categoriesCollection() =
        auth.currentUser
            ?.uid
            ?.let { userId ->

                firestore
                    .collection("users")
                    .document(userId)
                    .collection("categories")
            }


    fun listenToCategories(
        onCategoriesChanged:
            (List<ExpenseCategory>) -> Unit
    ) {

        listenerRegistration?.remove()

        val collection =
            categoriesCollection()
                ?: return

        listenerRegistration =
            collection
                .addSnapshotListener {
                        snapshot,
                        error ->

                    if (
                        error != null ||
                        snapshot == null
                    ) {
                        return@addSnapshotListener
                    }

                    val categories =
                        snapshot.documents.mapNotNull {
                                document ->

                            document
                                .toObject(
                                    ExpenseCategory::class.java
                                )
                                ?.copy(
                                    id = document.id
                                )
                        }

                    onCategoriesChanged(
                        categories
                    )
                }
    }


    fun addCategory(
        name: String,
        assignedAmount: Double
    ) {

        val collection =
            categoriesCollection()
                ?: return

        val document =
            collection.document()

        val category =
            ExpenseCategory(
                id = document.id,
                name = name,
                assignedAmount =
                    assignedAmount,
                remainingAmount =
                    assignedAmount
            )

        document.set(category)
    }


    fun updateRemainingAmount(
        categoryId: String,
        remainingAmount: Double
    ) {

        categoriesCollection()
            ?.document(categoryId)
            ?.update(
                "remainingAmount",
                remainingAmount
            )
    }


    fun stopListening() {
        listenerRegistration?.remove()
    }
}